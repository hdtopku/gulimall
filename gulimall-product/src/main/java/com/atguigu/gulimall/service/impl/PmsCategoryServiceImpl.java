package com.atguigu.gulimall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.service.PmsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @createTime 2022-03-05 12:58:37
 */
@RequiredArgsConstructor
@Service
public class PmsCategoryServiceImpl implements PmsCategoryService {
    private final CategoryService categoryService;

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> allCategories = categoryService.list();
        return allCategories.stream()
                .filter(item -> item.getParentCid() == 0)
                .map(item-> setChildren(item, allCategories))
                .sorted(Comparator.comparingInt(m -> ObjectUtil.defaultIfNull(m.getSort(), 0)))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeMenuByIds(List<Long> ids) {
//        TODO 1、检查当前删除的菜单，是否被别的地方引
//        2、执行批量删除
        return categoryService.removeByIds(ids);
    }

    private CategoryEntity setChildren(CategoryEntity parent, List<CategoryEntity> allCategories) {
        CategoryEntity categoryE = new CategoryEntity();
        BeanUtil.copyProperties(parent, categoryE);
        List<CategoryEntity> children = allCategories.stream()
                .filter(item-> item.getParentCid().equals(parent.getCatId()))
                .map(item-> setChildren(item, allCategories))
                .sorted(Comparator.comparingInt(m -> ObjectUtil.defaultIfNull(m.getSort(), 0)))
                .collect(Collectors.toList());
//        如果children为空，则不set。否则前端级联器会显示第四级，且为空白
//        if (CollUtil.isEmpty(children)) {
//            return categoryE;
//        }
        categoryE.setChildren(children);
        return categoryE;
    }
}
