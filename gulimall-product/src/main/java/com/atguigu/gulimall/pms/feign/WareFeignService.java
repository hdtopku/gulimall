package com.atguigu.gulimall.pms.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-18 22:43:21
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {

    @PostMapping("wms/waresku/hasStock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);
}
