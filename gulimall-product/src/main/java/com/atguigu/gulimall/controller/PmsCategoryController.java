package com.atguigu.gulimall.controller;

import com.atguigu.gulimall.mbg2.entity.PmsCategory;
import com.atguigu.gulimall.mbg2.service.IPmsCategoryService;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.service.PmsCategoryService;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-05 12:51:41
 */
@RestController
@RequestMapping(value ="pms/category")
@RequiredArgsConstructor
public class PmsCategoryController {
    private final PmsCategoryService pmsCategoryService;
    private final IPmsCategoryService iPmsCategoryService;

    @GetMapping("list/tree")
    public R<List<CategoryEntity>> list() {

        List<CategoryEntity> categories = pmsCategoryService.listWithTree();
        return R.ok(categories);
    }

    @PostMapping("delete")
    public R<Boolean> removeMenuByIds(@RequestBody List<Long> ids) {
        return R.ok(pmsCategoryService.removeMenuByIds(ids));
    }

    @PostMapping("update/sort")
    public R<Boolean> updateSort(@RequestBody List<PmsCategory> categories) {
        return R.ok(iPmsCategoryService.updateBatchById(categories));
    }

}
