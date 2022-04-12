package com.atguigu.gulimall.pms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.pms.feign.fallback.SeckillFeignServiceFallback;

/**
 * The interface Seckill feign service.
 */
@FeignClient(value = "gulimall-product", fallback = SeckillFeignServiceFallback.class)
public interface SeckillFeignService {
    /**
     * Gets sku seckill info.
     *
     * @param skuId the sku id
     * @return the sku seckill info
     */
    @GetMapping("sku/seckill/{skuId}")
    R getSkuSeckillInfo(@PathVariable("skuId") Long skuId);
}
