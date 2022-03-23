package com.atguigu.gulimall.pms.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.vo.Catalog2Vo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 找到catelogId的完整路径
     * [爷/父/当前节点]
     * @return
     */
    List<Long> findCategoryPath(Long categoryId);

    void updateCascade(CategoryEntity category);

    List<CategoryEntity> getLevel1Categorys();

    Map<String, List<Catalog2Vo>> getCatalogJson();
}

