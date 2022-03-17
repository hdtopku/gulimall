package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.to.SkuReduceTo;
import com.atguigu.gulimall.common.to.SpuBoundsTo;
import com.atguigu.gulimall.pms.dao.SpuInfoDao;
import com.atguigu.gulimall.pms.entity.*;
import com.atguigu.gulimall.pms.feign.CouponFeignService;
import com.atguigu.gulimall.pms.service.*;
import com.atguigu.gulimall.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
@RequiredArgsConstructor
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {
    private final SpuInfoDescService spuInfoDescService;
    private final SpuImagesService spuImagesService;
    private final AttrService attrService;
    private final ProductAttrValueService attrValueService;
    private final SkuInfoService skuInfoService;
    private final SkuImagesService skuImagesService;
    private final SkuSaleAttrValueService skuSaleAttrValueService;
    private final CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *
     * @param spuSaveVo
     * {"spuName":"华为Mate 30 Pro","spuDescription":"华为旗舰机","catalogId":225,"brandId":2,"weight":0.13,"publishStatus":0,"decript":["https://t7.baidu.com/it/u=2378812914,2178865774&fm=218&app=126&size=f242,150&n=0&f=PNG?s=4330806218FEB69E46B49A810300F0A1&sec=1647190800&t=ba1d62e03f3355f6a8ff3e55e24d2366"],"images":["https://t7.baidu.com/it/u=2378812914,2178865774&fm=218&app=126&size=f242,150&n=0&f=PNG?s=4330806218FEB69E46B49A810300F0A1&sec=1647190800&t=ba1d62e03f3355f6a8ff3e55e24d2366"],"bounds":{"buyBounds":0,"growBounds":0},"baseAttrs":[{"attrId":2,"attrValues":"2019","showDesc":1},{"attrId":3,"attrValues":"钛空银;霓影紫;极光蓝;流光幻镜","showDesc":0},{"attrId":5,"attrValues":"8G","showDesc":0},{"attrId":6,"attrValues":"套餐一","showDesc":0},{"attrId":9,"attrValues":"5000mAh","showDesc":1},{"attrId":11,"attrValues":"8GB+128GB","showDesc":0},{"attrId":13,"attrValues":"青春版","showDesc":1},{"attrId":14,"attrValues":"2019","showDesc":0}],"skus":[{"attr":[{"attrId":3,"attrName":"颜色","attrValue":"极光蓝"},{"attrId":6,"attrName":"套餐","attrValue":"套餐一"},{"attrId":11,"attrName":"版本","attrValue":"8GB+128GB"}],"skuName":"华为Mate 30 Pro 极光蓝 套餐一 8GB+128GB","price":"5388","skuTitle":"华为Mate 30 Pro 极光蓝 套餐一 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["极光蓝","套餐一","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"铜牌会员","price":0},{"id":3,"name":"银牌会员","price":0},{"id":4,"name":"金牌会员","price":0},{"id":5,"name":"钻石会员","price":0}]},{"attr":[{"attrId":3,"attrName":"颜色","attrValue":"极光蓝"},{"attrId":6,"attrName":"套餐","attrValue":"套餐一"},{"attrId":11,"attrName":"版本","attrValue":"8GB+256GB"}],"skuName":"华为Mate 30 Pro 极光蓝 套餐一 8GB+256GB","price":"5888","skuTitle":"华为Mate 30 Pro 极光蓝 套餐一 8GB+256GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["极光蓝","套餐一","8GB+256GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"铜牌会员","price":0},{"id":3,"name":"银牌会员","price":0},{"id":4,"name":"金牌会员","price":0},{"id":5,"name":"钻石会员","price":0}]},{"attr":[{"attrId":3,"attrName":"颜色","attrValue":"极光蓝"},{"attrId":6,"attrName":"套餐","attrValue":"套餐一"},{"attrId":11,"attrName":"版本","attrValue":"12GB+256GB"}],"skuName":"华为Mate 30 Pro 极光蓝 套餐一 12GB+256GB","price":"6388","skuTitle":"华为Mate 30 Pro 极光蓝 套餐一 12GB+256GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["极光蓝","套餐一","12GB+256GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"铜牌会员","price":0},{"id":3,"name":"银牌会员","price":0},{"id":4,"name":"金牌会员","price":0},{"id":5,"name":"钻石会员","price":0}]},{"attr":[{"attrId":3,"attrName":"颜色","attrValue":"极光蓝"},{"attrId":6,"attrName":"套餐","attrValue":"套餐二"},{"attrId":11,"attrName":"版本","attrValue":"8GB+128GB"}],"skuName":"华为Mate 30 Pro 极光蓝 套餐二 8GB+128GB","price":"6588","skuTitle":"华为Mate 30 Pro 极光蓝 套餐二 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["极光蓝","套餐二","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"铜牌会员","price":0},{"id":3,"name":"银牌会员","price":0},{"id":4,"name":"金牌会员","price":0},{"id":5,"name":"钻石会员","price":0}]},{"attr":[{"attrId":3,"attrName":"颜色","attrValue":"极光蓝"},{"attrId":6,"attrName":"套餐","attrValue":"套餐三"},{"attrId":11,"attrName":"版本","attrValue":"8GB+128GB"}],"skuName":"华为Mate 30 Pro 极光蓝 套餐三 8GB+128GB","price":"6588","skuTitle":"华为Mate 30 Pro 极光蓝 套餐三 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["极光蓝","套餐三","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"铜牌会员","price":0},{"id":3,"name":"银牌会员","price":0},{"id":4,"name":"金牌会员","price":0},{"id":5,"name":"钻石会员","price":0}]}]}
     */
    @Override
    @Transactional
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
//        1保存spu基本信息：pms_spu_info
        SpuInfoEntity infoEntity = BeanUtil.copyProperties(spuSaveVo, SpuInfoEntity.class);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.save(infoEntity);
//        2保存spu图片描述：pms_spu_info_desc
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(infoEntity.getId());
        if (CollUtil.isNotEmpty(spuSaveVo.getDecript())) {
            descEntity.setDecript(spuSaveVo.getDecript().stream().map(StrUtil::trim).collect(Collectors.joining(",")));
            spuInfoDescService.save(descEntity);
        }

//        3保存spu图片集：pms_spu_images
        List<String> imagesList = spuSaveVo.getImages();
        if (CollUtil.isNotEmpty(imagesList)) {
            List<SpuImagesEntity> imagesEntities = imagesList.stream().map(item->{
                SpuImagesEntity imagesEntity = new SpuImagesEntity();
                imagesEntity.setSpuId(infoEntity.getId());
                imagesEntity.setImgUrl(StrUtil.trim(item));
                return imagesEntity;
            }).filter(item-> StrUtil.isNotBlank(item.getImgUrl())).collect(Collectors.toList());
            spuImagesService.saveBatch(imagesEntities);
        }
//        4保存spu规格参数：pms_product_attr_value
        List<BaseAttrs> attrEntities = spuSaveVo.getBaseAttrs();
        if (CollUtil.isNotEmpty(attrEntities)) {
            List<ProductAttrValueEntity> valueEntities = attrEntities.stream().map(item->{
                ProductAttrValueEntity valueEntity = new ProductAttrValueEntity();
                AttrEntity entity = attrService.getById(item.getAttrId());
                if (ObjectUtil.isNotNull(entity)) {
                    valueEntity.setAttrName(entity.getAttrName());
                }
                valueEntity.setSpuId(infoEntity.getId());
                valueEntity.setAttrId(item.getAttrId());
                valueEntity.setAttrValue(item.getAttrValues());
                valueEntity.setQuickShow(item.getShowDesc());
                return valueEntity;
            }).collect(Collectors.toList());
            attrValueService.saveOrUpdateBatch(valueEntities);
        }
//        保存spu的积分信息：gulimall_sms->sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTo boundsTo = BeanUtil.copyProperties(bounds, SpuBoundsTo.class);
        boundsTo.setSpuId(infoEntity.getId());
        R r = couponFeignService.saveBounds(boundsTo);
        if (r.getCode() != 0) {
            log.error("远程保存spu积分信息失败");
            throw new RuntimeException("远程保存spu积分信息失败");
        }
//        5保存当前spu对应的sku信息：
        List<Skus> skusList = spuSaveVo.getSkus();
        if (CollUtil.isNotEmpty(skusList)) {
            //        5.1sku的基本信息:pms_sku_info
            skusList.forEach(item->{
                String defaultImg = null;
                if (CollUtil.isNotEmpty(item.getImages())) {
                    for(Images img : item.getImages()) {
                        if (img.getDefaultImg() == 1) {
                            defaultImg = img.getImgUrl();
                        }

                    }
                }
                SkuInfoEntity skuInfoEntity = BeanUtil.copyProperties(item, SkuInfoEntity.class);
                skuInfoEntity.setCatalogId(infoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setBrandId(infoEntity.getBrandId());
                skuInfoEntity.setSpuId(infoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.save(skuInfoEntity);
//        5.2sku的图片信息：pms_sku_images TODO 没有图片：路径无需保存

                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img->{
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    skuImagesEntity.setImgUrl(skuImagesEntity.getImgUrl());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);
//        5.3sku的销售属性信息：pms_sku_sale_attr_value
                List<Attr> attrList = item.getAttr();
                if (CollUtil.isNotEmpty(attrList)) {
                    List<SkuSaleAttrValueEntity> valueEntities= attrList.stream().map(a->{
                        SkuSaleAttrValueEntity valueEntity = BeanUtil.copyProperties(a, SkuSaleAttrValueEntity.class);
                        valueEntity.setSkuId(infoEntity.getId());
                        return valueEntity;
                    }).collect(Collectors.toList());
                    skuSaleAttrValueService.saveBatch(valueEntities);
                }
//        5.4sku的优惠满减信息：gulimall_sms->sms_sku_ladder、sms_sku_full_reduction、sms_member_price

                if (item.getFullCount() > 0 || item.getFullPrice().doubleValue() > 0) {
                    SkuReduceTo skuReduceTo = BeanUtil.copyProperties(item, SkuReduceTo.class);
                    skuReduceTo.setSkuId(skuInfoEntity.getSkuId());

                    R r1 = couponFeignService.saveSkuReduction(skuReduceTo);
                    if (r1.getCode() != 0) {
                        log.error("远程保存spu优惠信息失败");
                        throw new RuntimeException("远程保存spu优惠信息失败");
                    }
                }
            });
        }
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank((CharSequence) params.get("key"))) {
            wrapper.and(w-> w.eq("id", params.get("key")).or().like("spu_name", params.get("key")));
        }
        if (StrUtil.isNotBlank((CharSequence) params.get("status"))) {
            wrapper.and(w-> w.eq("publish_status", params.get("status")));
        }
        if (NumberUtil.parseInt((String)params.get("brandId")) > 0) {
            wrapper.and(w->{
                w.eq("brand_id", params.get("brandId"));
            });
        }
        if (NumberUtil.parseInt((String)params.get("catalogId")) > 0) {
            wrapper.and(w->{
                w.eq("catelog_id", params.get("catelogId"));
            });
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params),wrapper);
        return new PageUtils(page);
    }
}