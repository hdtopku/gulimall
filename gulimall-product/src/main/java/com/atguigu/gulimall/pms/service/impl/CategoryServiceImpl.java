package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.CategoryDao;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.service.CategoryBrandRelationService;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service("categoryService")
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    private final CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<Long> findCategoryPath(Long categoryId) {
        LinkedList<Long> categoryPath = new LinkedList<>();
        findParent(categoryId, categoryPath);
        return categoryPath;
    }
    private void findParent(Long categoryId, LinkedList<Long> categoryPath) {
        if (ObjectUtil.isNotNull(categoryId)) {
            categoryPath.addFirst(categoryId);
            CategoryEntity categoryEntity = this.getById(categoryId);
            if (ObjectUtil.isNotNull(categoryEntity) && categoryEntity.getParentCid() != 0) {
                findParent(categoryEntity.getParentCid(), categoryPath);
            }
        }
    }

    /**
     * 级联更新所有关联的数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StrUtil.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCascade(category.getCatId(), category.getName());
        }
    }

}