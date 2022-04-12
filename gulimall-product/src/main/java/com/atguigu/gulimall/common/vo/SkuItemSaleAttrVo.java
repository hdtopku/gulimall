package com.atguigu.gulimall.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-28 21:48:35
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}