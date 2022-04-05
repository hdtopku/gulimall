package com.atguigu.gulimall.pms.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-search")
public interface SearchFeignService {

    @PostMapping("search/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);
}
