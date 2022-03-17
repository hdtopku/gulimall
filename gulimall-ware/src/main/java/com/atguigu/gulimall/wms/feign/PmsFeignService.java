package com.atguigu.gulimall.wms.feign;

import com.atguigu.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("gulimall-sms")
public interface PmsFeignService {

    /**
     * /pms/skuinfo/info/{skuId} && @FeignClient("gulimall-sms") 不走网关
     * /api/pms/skuinfo/info/{skuId} && @FeignClient("gulimall-gateway") 走网关
     * @param skuId
     * @return
     */
    @RequestMapping("pms/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
