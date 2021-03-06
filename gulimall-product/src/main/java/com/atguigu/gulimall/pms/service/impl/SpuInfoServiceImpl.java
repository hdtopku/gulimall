package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.constant.ProductConstant;
import com.atguigu.gulimall.common.to.SkuHasStockTo;
import com.atguigu.gulimall.common.to.SkuReduceTo;
import com.atguigu.gulimall.common.to.SpuBoundsTo;
import com.atguigu.gulimall.common.to.es.SkuEsModel;
import com.atguigu.gulimall.pms.dao.SpuInfoDao;
import com.atguigu.gulimall.pms.entity.*;
import com.atguigu.gulimall.pms.feign.CouponFeignService;
import com.atguigu.gulimall.pms.feign.SearchFeignService;
import com.atguigu.gulimall.pms.feign.WareFeignService;
import com.atguigu.gulimall.pms.service.*;
import com.atguigu.gulimall.common.vo.*;
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
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final WareFeignService wareFeignService;
    private final SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * @param spuSaveVo {"spuName":"??????Mate 30 Pro","spuDescription":"???????????????","catalogId":225,"brandId":2,"weight":0.13,"publishStatus":0,"decript":["https://t7.baidu.com/it/u=2378812914,2178865774&fm=218&app=126&size=f242,150&n=0&f=PNG?s=4330806218FEB69E46B49A810300F0A1&sec=1647190800&t=ba1d62e03f3355f6a8ff3e55e24d2366"],"images":["https://t7.baidu.com/it/u=2378812914,2178865774&fm=218&app=126&size=f242,150&n=0&f=PNG?s=4330806218FEB69E46B49A810300F0A1&sec=1647190800&t=ba1d62e03f3355f6a8ff3e55e24d2366"],"bounds":{"buyBounds":0,"growBounds":0},"baseAttrs":[{"attrId":2,"attrValues":"2019","showDesc":1},{"attrId":3,"attrValues":"?????????;?????????;?????????;????????????","showDesc":0},{"attrId":5,"attrValues":"8G","showDesc":0},{"attrId":6,"attrValues":"?????????","showDesc":0},{"attrId":9,"attrValues":"5000mAh","showDesc":1},{"attrId":11,"attrValues":"8GB+128GB","showDesc":0},{"attrId":13,"attrValues":"?????????","showDesc":1},{"attrId":14,"attrValues":"2019","showDesc":0}],"skus":[{"attr":[{"attrId":3,"attrName":"??????","attrValue":"?????????"},{"attrId":6,"attrName":"??????","attrValue":"?????????"},{"attrId":11,"attrName":"??????","attrValue":"8GB+128GB"}],"skuName":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","price":"5388","skuTitle":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["?????????","?????????","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"????????????","price":0},{"id":3,"name":"????????????","price":0},{"id":4,"name":"????????????","price":0},{"id":5,"name":"????????????","price":0}]},{"attr":[{"attrId":3,"attrName":"??????","attrValue":"?????????"},{"attrId":6,"attrName":"??????","attrValue":"?????????"},{"attrId":11,"attrName":"??????","attrValue":"8GB+256GB"}],"skuName":"??????Mate 30 Pro ????????? ????????? 8GB+256GB","price":"5888","skuTitle":"??????Mate 30 Pro ????????? ????????? 8GB+256GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["?????????","?????????","8GB+256GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"????????????","price":0},{"id":3,"name":"????????????","price":0},{"id":4,"name":"????????????","price":0},{"id":5,"name":"????????????","price":0}]},{"attr":[{"attrId":3,"attrName":"??????","attrValue":"?????????"},{"attrId":6,"attrName":"??????","attrValue":"?????????"},{"attrId":11,"attrName":"??????","attrValue":"12GB+256GB"}],"skuName":"??????Mate 30 Pro ????????? ????????? 12GB+256GB","price":"6388","skuTitle":"??????Mate 30 Pro ????????? ????????? 12GB+256GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["?????????","?????????","12GB+256GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"????????????","price":0},{"id":3,"name":"????????????","price":0},{"id":4,"name":"????????????","price":0},{"id":5,"name":"????????????","price":0}]},{"attr":[{"attrId":3,"attrName":"??????","attrValue":"?????????"},{"attrId":6,"attrName":"??????","attrValue":"?????????"},{"attrId":11,"attrName":"??????","attrValue":"8GB+128GB"}],"skuName":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","price":"6588","skuTitle":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["?????????","?????????","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"????????????","price":0},{"id":3,"name":"????????????","price":0},{"id":4,"name":"????????????","price":0},{"id":5,"name":"????????????","price":0}]},{"attr":[{"attrId":3,"attrName":"??????","attrValue":"?????????"},{"attrId":6,"attrName":"??????","attrValue":"?????????"},{"attrId":11,"attrName":"??????","attrValue":"8GB+128GB"}],"skuName":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","price":"6588","skuTitle":"??????Mate 30 Pro ????????? ????????? 8GB+128GB","skuSubtitle":"","images":[{"imgUrl":"","defaultImg":0}],"descar":["?????????","?????????","8GB+128GB"],"fullCount":0,"discount":0,"countStatus":0,"fullPrice":0,"reducePrice":0,"priceStatus":0,"memberPrice":[{"id":2,"name":"????????????","price":0},{"id":3,"name":"????????????","price":0},{"id":4,"name":"????????????","price":0},{"id":5,"name":"????????????","price":0}]}]}
     */
    @Override
    @Transactional
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
//        1??????spu???????????????pms_spu_info
        SpuInfoEntity infoEntity = BeanUtil.copyProperties(spuSaveVo, SpuInfoEntity.class);
        infoEntity.setCreateTime(new Date());
        infoEntity.setUpdateTime(new Date());
        this.save(infoEntity);
//        2??????spu???????????????pms_spu_info_desc
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(infoEntity.getId());
        if (CollUtil.isNotEmpty(spuSaveVo.getDecript())) {
            descEntity.setDecript(spuSaveVo.getDecript().stream().map(StrUtil::trim).collect(Collectors.joining(",")));
            spuInfoDescService.save(descEntity);
        }

//        3??????spu????????????pms_spu_images
        List<String> imagesList = spuSaveVo.getImages();
        if (CollUtil.isNotEmpty(imagesList)) {
            List<SpuImagesEntity> imagesEntities = imagesList.stream().map(item -> {
                SpuImagesEntity imagesEntity = new SpuImagesEntity();
                imagesEntity.setSpuId(infoEntity.getId());
                imagesEntity.setImgUrl(StrUtil.trim(item));
                return imagesEntity;
            }).filter(item -> StrUtil.isNotBlank(item.getImgUrl())).collect(Collectors.toList());
            spuImagesService.saveBatch(imagesEntities);
        }
//        4??????spu???????????????pms_product_attr_value
        List<BaseAttrs> attrEntities = spuSaveVo.getBaseAttrs();
        if (CollUtil.isNotEmpty(attrEntities)) {
            List<ProductAttrValueEntity> valueEntities = attrEntities.stream().map(item -> {
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
//        ??????spu??????????????????gulimall_sms->sms_spu_bounds
        Bounds bounds = spuSaveVo.getBounds();
        SpuBoundsTo boundsTo = BeanUtil.copyProperties(bounds, SpuBoundsTo.class);
        boundsTo.setSpuId(infoEntity.getId());
        R r = couponFeignService.saveBounds(boundsTo);
        if (r.getCode() != 0) {
            log.error("????????????spu??????????????????");
            throw new RuntimeException("????????????spu??????????????????");
        }
//        5????????????spu?????????sku?????????
        List<Skus> skusList = spuSaveVo.getSkus();
        if (CollUtil.isNotEmpty(skusList)) {
            //        5.1sku???????????????:pms_sku_info
            skusList.forEach(item -> {
                String defaultImg = null;
                if (CollUtil.isNotEmpty(item.getImages())) {
                    for (Images img : item.getImages()) {
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
//        5.2sku??????????????????pms_sku_images TODO ?????????????????????????????????

                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuInfoEntity.getSkuId());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    skuImagesEntity.setImgUrl(skuImagesEntity.getImgUrl());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);
//        5.3sku????????????????????????pms_sku_sale_attr_value
                List<Attr> attrList = item.getAttr();
                if (CollUtil.isNotEmpty(attrList)) {
                    List<SkuSaleAttrValueEntity> valueEntities = attrList.stream().map(a -> {
                        SkuSaleAttrValueEntity valueEntity = BeanUtil.copyProperties(a, SkuSaleAttrValueEntity.class);
                        valueEntity.setSkuId(infoEntity.getId());
                        return valueEntity;
                    }).collect(Collectors.toList());
                    skuSaleAttrValueService.saveBatch(valueEntities);
                }
//        5.4sku????????????????????????gulimall_sms->sms_sku_ladder???sms_sku_full_reduction???sms_member_price

                if (item.getFullCount() > 0 || item.getFullPrice().doubleValue() > 0) {
                    SkuReduceTo skuReduceTo = BeanUtil.copyProperties(item, SkuReduceTo.class);
                    skuReduceTo.setSkuId(skuInfoEntity.getSkuId());

                    R r1 = couponFeignService.saveSkuReduction(skuReduceTo);
                    if (r1.getCode() != 0) {
                        log.error("????????????spu??????????????????");
                        throw new RuntimeException("????????????spu??????????????????");
                    }
                }
            });
        }
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank((CharSequence) params.get("key"))) {
            wrapper.and(w -> w.eq("id", params.get("key")).or().like("spu_name", params.get("key")));
        }
        if (StrUtil.isNotBlank((CharSequence) params.get("status"))) {
            wrapper.and(w -> w.eq("publish_status", params.get("status")));
        }
        if (NumberUtil.parseInt((String) params.get("brandId")) > 0) {
            wrapper.and(w -> {
                w.eq("brand_id", params.get("brandId"));
            });
        }
        if (NumberUtil.parseInt((String) params.get("catalogId")) > 0) {
            wrapper.and(w -> {
                w.eq("catelog_id", params.get("catelogId"));
            });
        }
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    /**
     * ????????????
     *
     * @param spuId
     */
    @Override
    @Transactional
    public void up(Long spuId) {
//        ???????????????????????????
        List<SkuInfoEntity> skus = skuInfoService.getSkusBySpuId(spuId);
//                ????????????sku???????????????????????????????????????
        List<ProductAttrValueEntity> baseAttrs = attrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = baseAttrs.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        QueryWrapper<AttrEntity> attrWrapper = new QueryWrapper<AttrEntity>().in("attr_id", attrIds).and(w -> w.eq("search_type", 1));
        List<Long> searchAttrIds = attrService.list(attrWrapper).stream().map(AttrEntity::getAttrId).collect(Collectors.toList());
        List<SkuEsModel.Attrs> attrsList = baseAttrs.stream().filter(item -> CollUtil.contains(searchAttrIds, item.getAttrId()))
                .map(item -> BeanUtil.copyProperties(item, SkuEsModel.Attrs.class)).collect(Collectors.toList());
//                ???????????????????????????
        R hasStock = wareFeignService.getSkusHasStock(skus.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList()));
        Map<Long, Boolean> skuIdStockMap = null;
        try {
            JSONArray stockStr = JSONUtil.parseArray(hasStock.get("data"));
            List<SkuHasStockTo> vos = JSONUtil.toList(stockStr, SkuHasStockTo.class);
            skuIdStockMap = vos.stream().collect(Collectors.toMap(SkuHasStockTo::getSkuId, SkuHasStockTo::getHasStock));
        } catch (Exception e) {
            log.error("???????????????????????????{}", e);
        }
        Map<Long, Boolean> finalSkuIdStockMap = skuIdStockMap;
        List<SkuEsModel> upProducts = skus.stream().map(item -> {
//        ?????????????????????
            SkuEsModel esModel = BeanUtil.copyProperties(item, SkuEsModel.class);
//                skuPrice/skuImg/
            esModel.setSkuPrice(item.getPrice());
            esModel.setSkuImg(item.getSkuDefaultImg());
//                hasStock/hotScore
//                ???????????????????????????
            if (null == finalSkuIdStockMap) {
                esModel.setHasStock(finalSkuIdStockMap.get(item.getSkuId()));
            } else {
                esModel.setHasStock(true);
            }
//                TODO ??????????????????????????????0
            esModel.setHotScore(0L);
//                ????????????:brandName/brandImg/
            BrandEntity brandEntity = brandService.getById(item.getBrandId());
            if (null != brandEntity) {
                esModel.setBrandName(brandEntity.getName());
                esModel.setBrandImg(brandEntity.getLogo());
            }
//                ???????????????:catalogName
            CategoryEntity categoryEntity = categoryService.getById(esModel.getCatalogId());
            if (null != categoryEntity) {
                esModel.setCatalogName(categoryEntity.getName());
            }
//                ??????????????????List<Attrs> attrs
            esModel.setAttrs(attrsList);
            return esModel;
        }).collect(Collectors.toList());
//                ??????????????????Es????????????
        R r = searchFeignService.productStatusUp(upProducts);
        if (r.getCode() == 0) {
//            ??????????????????
            baseMapper.updateSpuStatus(spuId, ProductConstant.StatusEnum.SPU_UP.getCode());
        } else {
//            TODO ????????????????????????????????????????????????
        }
    }
}