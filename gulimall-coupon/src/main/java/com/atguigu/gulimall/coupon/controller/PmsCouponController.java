package com.atguigu.gulimall.coupon.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.sms.entity.CouponEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Objects;

/**
 * @author lxh
 * @createTime 2022-03-01 21:08:13
 */
@RestController
@RequestMapping("coupon")
//启用nacos配置中心配置动态刷新
@RefreshScope
public class PmsCouponController {
//    @Value("${coupon.value}")
//    private String value;

    @GetMapping("member/list")
    public R memberCoupons() {
        CouponEntity coupon = new CouponEntity();
        coupon.setCouponName("满100减10");
        return R.ok().put("coupons", Collections.singletonList(coupon));
    }

    @GetMapping("test")
    public R test() {
        return Objects.requireNonNull(R.ok().put("value", 1));
    }

}
