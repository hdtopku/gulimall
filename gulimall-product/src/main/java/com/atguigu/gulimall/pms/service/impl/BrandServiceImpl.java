package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.BrandDao;
import com.atguigu.gulimall.pms.entity.BrandEntity;
import com.atguigu.gulimall.pms.service.BrandService;
import com.atguigu.gulimall.pms.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


@Service("brandService")
@RequiredArgsConstructor
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    private final CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(key)) {
            wrapper.eq("brand_id", key).or().eq("first_letter", key)
                    .or().like("name", key)
                    .or().like("descript", key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void updateDetail(BrandEntity brand) {
        this.updateById(brand);
        if (StrUtil.isNotBlank(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
    }

}