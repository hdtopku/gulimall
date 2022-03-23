package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-19 11:10:16
 */
public interface ProductSaveService {
    Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
