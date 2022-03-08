package com.atguigu.gulimall.service;

import com.atguigu.gulimall.pms.entity.CategoryEntity;

import java.util.List;

public interface PmsCategoryService {
    List<CategoryEntity> listWithTree();

    boolean removeMenuByIds(List<Long> ids);
}
