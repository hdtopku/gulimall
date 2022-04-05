package com.atguigu.gulimall.vo;

import com.atguigu.gulimall.pms.entity.SkuImagesEntity;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;
import com.atguigu.gulimall.pms.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-28 18:52:39
 */
@Data
public class SkuItemVo {
    //        1、sku基本信息获取
    SkuInfoEntity info;
    //        2、sku图片信息
    List<SkuImagesEntity> images;
    //        3、获取spu销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;
    //        4、获取spu的介绍
    List<SpuItemAttrGroupVo> groupAttrs;
    //        5、获取sku规格信息
    SpuInfoDescEntity desp;

}

