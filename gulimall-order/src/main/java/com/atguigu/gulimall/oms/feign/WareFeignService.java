package com.atguigu.gulimall.oms.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimall-ware")
public interface WareFeignService {
    @GetMapping("wms/waresku/addStock")
    R addSkuStock();
}
