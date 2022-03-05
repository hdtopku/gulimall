package com.atguigu.gulimall.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.atguigu.gulimall.entity.PmsCategoryE;
import com.atguigu.gulimall.mbg.entity.PmsCategory;
import com.atguigu.gulimall.mbg.service.IPmsCategoryService;
import com.atguigu.gulimall.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxh
 * @Description TODO
 * @createTime 2022-03-05 12:58:37
 */
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final IPmsCategoryService iPmsCategoryService;

    @Override
    public List<PmsCategoryE> listWithTree() {
        List<PmsCategory> allCategories = iPmsCategoryService.list();
        return allCategories.stream()
                .filter(item -> item.getParentCid() == 0)
                .map(item-> setChildren(item, allCategories))
                .sorted(Comparator.comparingInt(m -> ObjectUtil.defaultIfNull(m.getSort(), 0)))
                .collect(Collectors.toList());
    }
    private PmsCategoryE setChildren(PmsCategory parent, List<PmsCategory> allCategories) {
        PmsCategoryE categoryE = new PmsCategoryE();
        BeanUtil.copyProperties(parent, categoryE);
        categoryE.setChildren(allCategories.stream()
                .filter(item-> item.getParentCid().equals(parent.getCatId()))
                .map(item-> setChildren(item, allCategories))
                .sorted(Comparator.comparingInt(m -> ObjectUtil.defaultIfNull(m.getSort(), 0)))
                .collect(Collectors.toList()));
        return categoryE;
    }
}
