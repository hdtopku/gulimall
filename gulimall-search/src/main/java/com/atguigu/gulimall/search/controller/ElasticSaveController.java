package com.atguigu.gulimall.search.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.common.utils.BizCodeEnum;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-19 11:07:48
 */
@RestController
@RequestMapping("search")
@RequiredArgsConstructor
@Slf4j
public class ElasticSaveController {
    private final ProductSaveService productSaveService;

//    上架商品
    @PostMapping("product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        try {
            productSaveService.productStatusUp(skuEsModels);
            return R.ok();
        } catch (IOException e) {
            log.error("ElasticSaveController商品上架错误: {}", ExceptionUtil.stacktraceToString(e));
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }

}
