package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.SkuInfoDao;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;
import com.atguigu.gulimall.pms.service.SkuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
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
}