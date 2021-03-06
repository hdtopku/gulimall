package com.atguigu.gulimall.search.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.search.config.EsConfig;
import com.atguigu.gulimall.search.constant.EsConstant;
import com.atguigu.gulimall.search.service.MallSearchService;
import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @createTime 2022-03-24 00:25:42
 */
@Service
@RequiredArgsConstructor
public class MallSearchServiceImpl implements MallSearchService {
    private final RestHighLevelClient client;

    @Override
    public Object search(SearchParam param) {
//        ?????????????????????????????????DSL??????
        SearchResult result = null;
//        ??????????????????
        SearchRequest searchRequest = buildSearchRequest(param);
//        ??????????????????
        try {
            SearchResponse response = client.search(searchRequest, EsConfig.COMMON_OPTIONS);
//            3???????????????????????????????????????????????????
            result = buildSearchResult(response, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ??????????????????
     * @param response
     * @return
     */
    private SearchResult buildSearchResult(SearchResponse response, SearchParam param) {
        SearchResult result = new SearchResult();
//        ?????????????????????????????????
        SearchHits hits = response.getHits();
        long total = hits.getTotalHits().value;
        result.setTotal(total);
        int totalPages = (int) ((total -1) / EsConstant.PRODUCT_PAGESIZE + 1);
        result.setTotalPages(totalPages);
        result.setPageNumber(param.getPageNumber());
        List<SkuEsModel> esModelList = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            SkuEsModel skuEsModel = JSONUtil.toBean(hit.getSourceAsString(), SkuEsModel.class);
            if (StrUtil.isNotBlank(param.getKeyword())) {
                String sku_title = hit.getHighlightFields().get("skuTitle").getFragments()[0].toString();
                skuEsModel.setSkuTitle(sku_title);
            }
            esModelList.add(skuEsModel);
        }
        result.setProducts(esModelList);
//        ??????????????????????????????????????????
        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested attr_agg = response.getAggregations().get("attr_agg");
        ParsedLongTerms attr_id_agg = attr_agg.getAggregations().get("attr_id_agg");
        for (Terms.Bucket bucket : attr_id_agg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            attrVo.setAttrId(bucket.getKeyAsNumber().longValue());
            ParsedStringTerms attr_name_agg = bucket.getAggregations().get("attr_name_agg");
            attrVo.setAttrName(attr_name_agg.getBuckets().get(0).getKeyAsString());
            ParsedStringTerms attr_value_agg = bucket.getAggregations().get("attr_value_agg");
            attrVo.setAttrValue(attr_value_agg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString)
                    .collect(Collectors.toList()));
            attrVos.add(attrVo);
        }
        result.setAttrs(attrVos);

//        ?????????????????????????????????????????????
        List<SearchResult.BrandVo> brandVoList = new ArrayList<>();
        ParsedLongTerms brand_agg = response.getAggregations().get("brand_agg");
        for (Terms.Bucket bucket : brand_agg.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            brandVo.setBrandId(bucket.getKeyAsNumber().longValue());
            ParsedStringTerms stringTerms1 = bucket.getAggregations().get("brand_name_agg");
            String brandName = stringTerms1.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brandName);
            ParsedStringTerms stringTerms = bucket.getAggregations().get("brand_img_agg");
            String brandImg = stringTerms.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brandImg);
            brandVoList.add(brandVo);
        }
        result.setBrands(brandVoList);

//        ?????????????????????????????????????????????
        List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        Terms aggregation = response.getAggregations().get("catalog_agg");
        if (CollUtil.isNotEmpty(aggregation.getBuckets())) {
            ParsedLongTerms catalog_agg = (ParsedLongTerms) aggregation;
            for (Terms.Bucket bucket : catalog_agg.getBuckets()) {
//            ????????????id
                SearchResult.CatalogVo vo = new SearchResult.CatalogVo();
                vo.setCatalogId(bucket.getKeyAsNumber().longValue());
//            ??????????????????
                ParsedStringTerms stringTerms = bucket.getAggregations().get("catalog_name_agg");
                vo.setCatalogName(stringTerms.getBuckets().get(0).getKeyAsString());
            }
            result.setCatalogs(catalogVos);
        }

        return result;
    }

    /**
     * ??????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    private SearchRequest buildSearchRequest(SearchParam param) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        ???????????????????????????????????????????????????????????????????????????????????????
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StrUtil.isNotBlank(param.getKeyword())) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
//        ??????3???????????????
        if (null != param.getCatalog3Id()) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
//        ????????????id
        if (null != param.getBrandId() && param.getBrandId().size() > 0) {
            boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
//        ???????????????????????????attrs=1_5???:8???&attrs=2_16G:8G
        if (null != param.getAttrs() && param.getAttrs().size() > 0) {
            for (String attrStr : param.getAttrs()) {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                String[] s = attrStr.split("_");
                String attrId = s[0];
                String[] attrValues = s[1].split(":");
                nestedBoolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", nestedBoolQuery, ScoreMode.None);
                boolQueryBuilder.filter(nestedQuery);
            }
        }
//        ???????????????????????????
        boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));
//        skuPrice=1_500/_500/500_
        if (StrUtil.isNotBlank(param.getSkuPrice())) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("skuPrice");
            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2) {
                rangeQuery.gte(s[0]).lte(s[1]);
            } else if (s.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    rangeQuery.lte(s[0]);
                }
                if (param.getSkuPrice().endsWith("_")) {
                    rangeQuery.gte(s[0]);
                }
            }
            boolQueryBuilder.filter(rangeQuery);
        }
//        ????????????????????????????????????
        sourceBuilder.query(boolQueryBuilder);

//        ??????
        if (StrUtil.isNotBlank(param.getSort())) {
            String sort = param.getSort();
            String[] s = sort.split("_");
            SortOrder order = "asc".equalsIgnoreCase(s[1]) ? SortOrder.ASC : SortOrder.DESC;
            sourceBuilder.sort(s[0], order);
        }
//        ?????? pageSize:5
//        pageNum: 1 from: 0 size:5
//        pageNum: 2 from: 5 size:5
        if (null != param.getPageNumber()) {
            sourceBuilder.from((param.getPageNumber() - 1) * EsConstant.PRODUCT_PAGESIZE);
        }
        sourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
//        ??????
        if (StrUtil.isNotBlank(param.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            sourceBuilder.highlighter(highlightBuilder);
        }
//        ????????????
        /**????????????*/
        TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
        brand_agg.field("brandId").size(50);
//        ????????????????????????
        brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
        brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
        sourceBuilder.aggregation(brand_agg);
        /**????????????*/
        TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg").field("catalog_id").size(20);
        brand_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
        sourceBuilder.aggregation(catalog_agg);
        /**????????????*/
        NestedAggregationBuilder nested = AggregationBuilders.nested("attr_agg", "attrs");
//        ????????????????????????attrId
        TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
//        ?????????????????????attr_id?????????name
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
//        ?????????????????????attr_id?????????value
        attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
        nested.subAggregation(attr_id_agg);
        sourceBuilder.aggregation(nested);
        return new SearchRequest(new String[]{
                EsConstant.PRODUCT_INDEX
        }, sourceBuilder);
    }
}
