package com.atguigu.gulimall.seckill.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.seckill.feign.CouponFeignService;
import com.atguigu.gulimall.seckill.feign.ProductFeignService;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SecKillSkuRedisTo;
import com.atguigu.gulimall.seckill.vo.SeckillSessionsWithSkus;
import com.atguigu.gulimall.seckill.vo.SeckillSkuVo;
import com.atguigu.gulimall.seckill.vo.SkuInfoVo;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The type Seckill service.
 *
 * @author lxh
 * @createTime 2022 -04-10 22:50:55
 */
@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {
    private static final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private static final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    /*商品随机码*/
    private static final String SKU_STOCK_SEMAPHORE = "seckill:stock:";
    private final CouponFeignService couponFeignService;
    private final StringRedisTemplate redisTemplate;
    private final ProductFeignService productFeignService;
    private final RedissonClient redissonClient;

    @Override
    public void uploadSeckillSkuLatest3Days() {
        R session = couponFeignService.getLates3DaySession();
        if (session.getCode() == 0) {
            SeckillSessionsWithSkus data = session.getData(new TypeReference<SeckillSessionsWithSkus>() {});
        }

    }

    @Override
    public List<SecKillSkuRedisTo> getCurrentSeckillSkus() {
        long time = new DateTime().getTime();
        Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
        if (CollUtil.isNotEmpty(keys)) {
            for (String key : keys) {
                String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
                String[] s = replace.split("_");
                long start = Long.parseLong(s[0]);
                long end = Long.parseLong(s[1]);
                if (time >= start && time <= end) {
                    List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                    BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
                    List<Object> list = ops.multiGet(Collections.singleton(range));
                    return Optional.ofNullable(list).orElse(Collections.emptyList()).stream()
                        .map(item -> BeanUtil.copyProperties(item, SecKillSkuRedisTo.class))
                        .collect(Collectors.toList());
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public SecKillSkuRedisTo getSkuSeckillInfo(Long skuId) {
        // 找到需要参与秒杀的商品key
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        String regx = "\\d_" + skuId;
        for (String key : ops.keys()) {
            if (Pattern.matches(regx, ops.get(key))) {
                SecKillSkuRedisTo redisTo = JSONUtil.toBean(ops.get(key), SecKillSkuRedisTo.class);
                long current = new DateTime().getTime();
                if (!(current >= redisTo.getStartTime() && current <= redisTo.getEndTime())) {
                    redisTo.setRandomCode(null);
                }
                return JSONUtil.toBean(ops.get(key), SecKillSkuRedisTo.class);
            }
        }
        return null;
    }

    @Override
    public String secKill(String killId, String key, Integer num) {
//        获取商品详细信息
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
        String s = ops.get(killId);
        if (StrUtil.isNotBlank(s)) {
            SecKillSkuRedisTo redisTo = JSONUtil.toBean(s, SecKillSkuRedisTo.class);
//            校验合法性
            Long time = new DateTime().getTime();
            Long startTime = redisTo.getStartTime();
            Long endTime = redisTo.getEndTime();
            if (time >= startTime && time <= endTime) {
                String redisKillId = redisTo.getPromotionSessionId() + "_" + redisTo.getSkuId();
//                校验随机码和商品id是否正确
                if (redisTo.getRandomCode().equals(key) && redisKillId.equals(killId)) {

                } else {
                    return null;
                }
            }

        }
        return null;
    }

    private void saveSessionInfos(List<SeckillSessionsWithSkus> sessions) {
        sessions.stream().forEach(item -> {
            long startTime = item.getStartTime().getTime();
            long endTime = item.getEndTime().getTime();
            String key = CharSequenceUtil.format("%s_%s", startTime, endTime);
            List<String> collect =
                item.getRelactionSkus().stream().map(val -> String.valueOf(val.getId())).collect(Collectors.toList());
            Boolean hasKey = redisTemplate.hasKey(SeckillServiceImpl.SESSIONS_CACHE_PREFIX + key);
            if (Boolean.FALSE.equals(hasKey)) {
                redisTemplate.opsForList().leftPushAll(SeckillServiceImpl.SESSIONS_CACHE_PREFIX + key, collect);
            }
        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionsWithSkus> sessions) {
        sessions.forEach(item -> {
            BoundHashOperations<String, Object, Object> ops =
                redisTemplate.boundHashOps(SeckillServiceImpl.SKUKILL_CACHE_PREFIX);
            for (SeckillSkuVo val : item.getRelactionSkus()) {
                if (Boolean.TRUE.equals(ops.hasKey(val.getSkuId()))) {
                    continue;
                }
                // 缓存商品信息
                SecKillSkuRedisTo redisTo = BeanUtil.copyProperties(item, SecKillSkuRedisTo.class);
                // sku的基本数据
                R skuInfo = productFeignService.getSkuInfo(val.getSkuId());
                if (skuInfo.getCode() == 0) {
                    SkuInfoVo info = skuInfo.getData("skuInfo", new TypeReference<SkuInfoVo>() {});
                    redisTo.setSkuInfo(info);
                }
                // 设置上当前商品的秒杀时间信息
                redisTo.setStartTime(item.getStartTime().getTime());
                redisTo.setEndTime(item.getEndTime().getTime());
                // 随机码，防止脚本反复秒杀攻击
                String token = IdUtil.simpleUUID();
                redisTo.setRandomCode(token);
                // 使用库存作为分布式信号量 限流
                if (Boolean.FALSE.equals(redisTemplate.hasKey(SKU_STOCK_SEMAPHORE + token))) {
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    semaphore.trySetPermits(val.getSeckillCount());
                    ops.put(item.getId(), JSONUtil.toJsonStr(item));
                }
            }
        });
    }

}
