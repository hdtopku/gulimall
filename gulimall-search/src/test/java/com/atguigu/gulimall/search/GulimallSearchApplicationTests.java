package com.atguigu.gulimall.search;

import cn.hutool.json.JSONUtil;
import com.atguigu.gulimall.search.config.EsConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class GulimallSearchApplicationTests {
    @Resource
    private RestHighLevelClient client;
    @Test
    void contextLoads() {
        System.out.println(client);
    }
    @Data
    static class Account {

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }
    @Test
    void initData() {
        IndexRequest request = new IndexRequest();
        request.id("1");
    }
    @Test
    void searchData() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
//        指定索引
        searchRequest.indices("bank");
//        指定DSL，检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        构造检索条件
//        sourceBuilder.query();
//        sourceBuilder.size();
//        sourceBuilder.from();
//        sourceBuilder.aggregation();
        sourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
//        按照年龄聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10));
//        计算平均薪资
        sourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));
        searchRequest.source(sourceBuilder);
//        执行检索
        SearchResponse searchResponse = client.search(searchRequest, EsConfig.COMMON_OPTIONS);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit: hits) {
//            hit.getId();hit.getType();hit.getIndex();
            String string = hit.getSourceAsString();
            Account account = JSONUtil.toBean(string, Account.class);
            System.out.println(account);
        }
//        分析结果
        Terms aggAgg = searchResponse.getAggregations().get("ageAgg");
        for (Terms.Bucket bucket: aggAgg.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄：" + keyAsString + "===>" + bucket.getDocCount());
        }
        Avg balanceAvg = searchResponse.getAggregations().get("balanceAvg");
        System.out.println("平均薪资：" + balanceAvg.getValue());
        System.out.println(searchResponse.toString());
    }

    /**
     * 方案一：冗余
     * {
     * skuId:1
     * skuTitle:华为mate 30 pro
     * price: 998
     * saleCount: 99
     * attrs:[{尺寸: 5寸}, {cpu: 高通945}, {分辨率:全高清}]
     * }
     * 方案二
     * {
     *  skuId:1,
     *  attrs: [{尺寸: 5寸}, {cpu: 高通945}, {分辨率:全高清}]
     */
}
