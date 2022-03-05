package com.atguigu.gulimall.service;

import com.atguigu.gulimall.entity.PmsCategoryE;

import java.util.List;

public interface CategoryService {
    List<PmsCategoryE> listWithTree();
}
