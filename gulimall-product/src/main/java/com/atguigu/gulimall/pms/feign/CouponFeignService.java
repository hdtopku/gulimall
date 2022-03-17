package com.atguigu.gulimall.pms.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.to.SkuReduceTo;
import com.atguigu.gulimall.common.to.SpuBoundsTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-sms")
public interface CouponFeignService {


    @PostMapping("sms/spubounds/save")
    R saveBounds(@RequestBody SpuBoundsTo boundsTo);

    @PostMapping("sms/skufullreduction/save")
    R saveSkuReduction(@RequestBody SkuReduceTo skuReduceTo);
}
