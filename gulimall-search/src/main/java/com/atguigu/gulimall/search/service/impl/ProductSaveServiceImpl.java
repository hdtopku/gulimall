package com.atguigu.gulimall.search.service.impl;

/**
 * @author lxh
 * @createTime 2022-03-19 11:10:44
 */

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.search.config.EsConfig;
import com.atguigu.gulimall.search.constant.EsConstant;
import com.atguigu.gulimall.search.service.ProductSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {
    private final RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
//        保存到Es BulkRequest bulkRequest, RequestOptions options

        BulkRequest bulkRequest = new BulkRequest();
        skuEsModels.forEach(item->{
            IndexRequest request = new IndexRequest(EsConstant.PRODUCT_INDEX);
            request.id(item.getSkuId().toString());
            String s = JSONUtil.toJsonStr(item);
            request.source(s, XContentType.JSON);
            bulkRequest.add(request);
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, EsConfig.COMMON_OPTIONS);
        Boolean b = bulk.hasFailures();
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.error("商品上架错误：{}", collect);
//        给es建立索引
        return b;
    }
}
