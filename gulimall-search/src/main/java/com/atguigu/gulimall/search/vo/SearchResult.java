package com.atguigu.gulimall.search.vo;

import com.atguigu.gulimall.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-24 18:47:41
 */
@Data
public class SearchResult {
//    查询到的所有商品信息
    private List<SkuEsModel> products;

    private Integer pageNumber;
    private Long total; //总记录数
    private Integer totalPages; //总页码
    private List<BrandVo> brands; // 关键字涉及的品牌
    private List<CatalogVo> catalogs;
    private List<AttrVo> attrs; // 当前查询到的结果，所有涉及到的所有属性

    @Data
    public static class BrandVo {
        private Long brandId;
        private String brandName;
        private String brandImg;
    }
    @Data
    public static class CatalogVo {
        private Long catalogId;
        private String catalogName;
    }

    @Data
    public static class AttrVo {
        private Long attrId;
        private String attrName;
        private List<String> attrValue;
    }

}
