package com.atguigu.gulimall.member.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lxh
 * @createTime 2022-03-01 22:09:27
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    @GetMapping("coupon/member/list")
    R memberCoupons();
}
