package com.atguigu.gulimall.seckill.service;

import com.atguigu.gulimall.seckill.to.SecKillSkuRedisTo;

import java.util.List;

/**
 * The interface Seckill service.
 */
public interface SeckillService {
    /**
     * Upload seckill sku latest 3 days.
     */
    void uploadSeckillSkuLatest3Days();

    /**
     * Gets current seckill skus.
     *
     * @return the current seckill skus
     */
    List<SecKillSkuRedisTo> getCurrentSeckillSkus();

    /**
     * Gets sku seckill info.
     *
     * @param skuId the skuId
     * @return the sku seckill info
     */
    SecKillSkuRedisTo getSkuSeckillInfo(Long skuId);

    /**
     * Sec kill string.
     *
     * @param killId the kill id
     * @param key    the key
     * @param num    the num
     * @return the string
     */
    String secKill(String killId, String key, Integer num);
    
}
