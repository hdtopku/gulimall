package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.SkuInfoDao;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;
import com.atguigu.gulimall.pms.service.*;
import com.atguigu.gulimall.vo.SkuItemVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;


@Service("skuInfoService")
@RequiredArgsConstructor
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {
    private final SkuImagesService skuImagesService;
    private final SpuInfoDescService spuInfoDescService;
    private final AttrGroupService attrGroupService;
    private final SkuSaleAttrValueService skuSaleAttrValueService;
    private final ThreadPoolExecutor executor;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank((CharSequence) params.get("key"))) {
            wrapper.and(w -> w.eq("sku_id", params.get("key")).or().like("sku_name", params.get("key")));
        }
        if (NumberUtil.parseInt((String) params.get("brandId")) > 0) {
            wrapper.eq("brand_id", params.get("brandId"));
        }
        if (NumberUtil.parseInt((String) params.get("catalogId")) > 0) {
            wrapper.eq("catelog_id", params.get("catelogId"));
        }
        String min = (String) params.get("min");
        if (StrUtil.isNotBlank(min) && new BigDecimal(min).doubleValue() > 0) {
            wrapper.ge("price", params.get("min"));
        }
        String max = (String) params.get("max");
        if (StrUtil.isNotBlank(max) && new BigDecimal(max).doubleValue() > 0) {
            wrapper.le("price", params.get("max"));
        }
        IPage<SkuInfoEntity> page = this.page(new Query<SkuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        return this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
//        1、sku基本信息获取
            SkuInfoEntity info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, executor);
        CompletableFuture<Void> saleFuture = infoFuture.thenAcceptAsync((info) -> {
//        3、获取spu销售属性组合
            skuItemVo.setSaleAttr(skuSaleAttrValueService.getSaleAttrsBySpuId(info.getSpuId()));
        }, executor);
        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((info) -> {
//        4、获取spu的介绍
            skuItemVo.setDesp(spuInfoDescService.getById(info.getSpuId()));
        }, executor);
        CompletableFuture<Void> attrFuture = infoFuture.thenAcceptAsync((info) -> {
//        5、获取sku规格信息
            skuItemVo.setGroupAttrs(attrGroupService.getAttrGroupWithAttrsBySpuId(info.getSpuId(), info.getCatalogId()));
        }, executor);
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
//        2、sku图片信息
            skuItemVo.setImages(skuImagesService.getImagesBySkuId(skuId));
        }, executor);
        CompletableFuture.allOf(saleFuture, descFuture, attrFuture, imageFuture).get();
        return skuItemVo;
    }
}