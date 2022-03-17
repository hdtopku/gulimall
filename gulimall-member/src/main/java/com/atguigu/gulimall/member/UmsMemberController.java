package com.atguigu.gulimall.member;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.member.feign.CouponFeignService;
import com.atguigu.gulimall.ums.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxh
 * @createTime 2022-03-01 22:07:36
 */
@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class UmsMemberController {
    private final CouponFeignService couponFeignService;

    @GetMapping("coupons")
    public R getCoupons() {
        MemberEntity member = new MemberEntity();
        member.setNickname("张三");
        R memberCoupons = couponFeignService.memberCoupons();
        return R.ok().put("member", member).put("coupons", memberCoupons.get("coupons"));
    }
}
