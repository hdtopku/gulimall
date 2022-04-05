package com.atguigu.gulimall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-24 00:23:39
 */
@Data
public class SearchParam {
    private String keyword;
    private Long catalog3Id;
    /**
     * sort=saleCount_asc/desc
     * sort=skuPrice_asc/desc
     * sort=hotScore_asc/desc
     */
    private String sort;
    /**
     * hotStock（是否有货）、skuPrice区间、brandId、catalog3Id、attrs
     * hotStock=0/1
     * skuPrice=1_500/_500/500_
     * &attrs=1_5寸:8寸&attrs=2_16G:8G
     * brandId=1&brandId=2
     */
    private Integer hasStock = 1; // 是否只显示有库存 0（无库存） 1（有库存）
    private String skuPrice; // 价格区间查询
    private List<Long> brandId;  //按品牌，可以多选
    private List<String> attrs; //商品属性进行筛选
    private Integer pageNumber; //页码

}
