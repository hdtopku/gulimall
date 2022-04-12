package com.atguigu.gulimall.seckill.config;

import com.atguigu.gulimall.seckill.service.SeckillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品上架配置 每天晚上3点：上架最近三天的秒杀商品
 * 
 * @author lxh
 * @createTime 2022-04-10 22:46:44
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ScheduleConfig {
    private final static String UPLOAD_LOCK = "seckill:upload:lock";
    private final SeckillService seckillService;
    private final RedissonClient redissonClient;

    /**
     * TODO 幂等性，上架过就不能再次上架，分布式情况下，多台机器可能多次执行
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void uploadSeckillSkuLatest3Days() {
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        try {
            boolean b = lock.tryLock(10, 20, TimeUnit.SECONDS);
            // 分布式环境防止重复上架
            if (b) {
                seckillService.uploadSeckillSkuLatest3Days();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
