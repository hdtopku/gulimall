package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsCategoryBrandRelationService;
import com.atguigu.gulimall.mbg2.entity.PmsCategoryBrandRelation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-category-brand-relation")
public class PmsCategoryBrandRelationAction {


    @Autowired
    private IPmsCategoryBrandRelationService iPmsCategoryBrandRelationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsCategoryBrandRelation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsCategoryBrandRelation> aPage = iPmsCategoryBrandRelationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsCategoryBrandRelation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsCategoryBrandRelationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsCategoryBrandRelation params) {
        iPmsCategoryBrandRelationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsCategoryBrandRelationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsCategoryBrandRelation params) {
        iPmsCategoryBrandRelationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
