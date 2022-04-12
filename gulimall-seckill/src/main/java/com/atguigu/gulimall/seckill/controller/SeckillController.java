package com.atguigu.gulimall.seckill.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SecKillSkuRedisTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Seckill controller.
 *
 * @author lxh
 * @createTime 2022 -04-11 19:03:34
 */
@RestController
@RequiredArgsConstructor
public class SeckillController {
    private final SeckillService seckillService;

    /**
     * Gets current seckill skus.
     * 返回当前方法可以参与的秒杀信息
     *
     * @return the current seckill skus
     */
    @GetMapping("/currentSeckillSkus")
    public R getCurrentSeckillSkus() {
        List<SecKillSkuRedisTo> skuRedisTos = seckillService.getCurrentSeckillSkus();
        return R.ok().put("data", skuRedisTos);
    }

    /**
     * Gets sku seckill info.
     *
     * @param skuId the sku id
     * @return the sku seckill info
     */
    @GetMapping("sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId) {
        SecKillSkuRedisTo to = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().put("data", to);
    }

    /**
     * Sec kill r.
     *
     * @param killId the kill id：sessionId_skuId
     * @param key    the key
     * @param num    the num
     * @return the r
     */
    @GetMapping("kill")
    public R secKill(@RequestParam("killId") String killId, @RequestParam("key") String key,
                     @RequestParam("num") Integer num) {
String orderSn = seckillService.secKill(killId, key, num);
        return R.ok().put("data", orderSn);
    }
}
