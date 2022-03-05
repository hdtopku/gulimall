package com.atguigu.gulimall.controller;

import com.atguigu.gulimall.entity.PmsCategoryE;
import com.atguigu.gulimall.service.CategoryService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxh
 * @Description TODO
 * @createTime 2022-03-05 12:51:41
 */
@RestController
@RequestMapping(value ="product/category")
@RequiredArgsConstructor
public class CatetoryController {
    private final CategoryService categoryService;

    @GetMapping("list/tree")
    public R<List<PmsCategoryE>> list() {
        List<PmsCategoryE> categories = categoryService.listWithTree();
        return R.ok(categories);
    }
}
