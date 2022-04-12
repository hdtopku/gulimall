package com.atguigu.gulimall.pms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.atguigu.gulimall.pms.entity.BrandEntity;
import com.atguigu.gulimall.common.vo.BrandVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.pms.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.pms.service.CategoryBrandRelationService;
import com.atguigu.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
@RestController
@RequestMapping("pms/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("brands/list")
    public R brandList(@RequestParam(value = "catId") Long catId) {
        List<BrandEntity> brandEntities =categoryBrandRelationService.getBrandsByCatId(catId);
        return R.ok().put("data", brandEntities.stream().map(
                value->BeanUtil.copyProperties(value, BrandVo.class)).collect(Collectors.toList()));
    }

    /**
     * 当前品牌关联的所有分类列表
     */
    @GetMapping("/list")
    @RequiresPermissions("pms:categorybrandrelation:list")
    public R list(@RequestParam Long brandId){
        return R.ok().put("data", categoryBrandRelationService.list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId)));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("pms:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("pms:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
        categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("pms:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("pms:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
