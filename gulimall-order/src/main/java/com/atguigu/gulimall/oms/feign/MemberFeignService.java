package com.atguigu.gulimall.oms.feign;

import com.atguigu.gulimall.oms.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient("gulimall-member")
public interface MemberFeignService {
    @GetMapping("ums/memberreceiveaddress/{memberId}/addresses")
    List<MemberAddressVo> getAddresses(@PathVariable("memberId") Long memberId);
}
