package com.atguigu.gulimall.seckill.feign;

import com.atguigu.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The interface Product feign service.
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     * Gets sku info.
     *
     * @param skuId the sku id
     * @return the sku info
     */
    @RequestMapping("pms/skuinfo/info/{skuId}")
    @RequiresPermissions("pms:skuinfo:info")
    R getSkuInfo(@PathVariable("skuId") Long skuId);
}
