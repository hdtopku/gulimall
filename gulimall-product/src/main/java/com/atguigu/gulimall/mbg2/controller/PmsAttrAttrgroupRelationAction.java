package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsAttrAttrgroupRelationService;
import com.atguigu.gulimall.mbg2.entity.PmsAttrAttrgroupRelation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 属性&属性分组关联 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-attr-attrgroup-relation")
public class PmsAttrAttrgroupRelationAction {


    @Autowired
    private IPmsAttrAttrgroupRelationService iPmsAttrAttrgroupRelationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsAttrAttrgroupRelation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsAttrAttrgroupRelation> aPage = iPmsAttrAttrgroupRelationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsAttrAttrgroupRelation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsAttrAttrgroupRelationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsAttrAttrgroupRelation params) {
        iPmsAttrAttrgroupRelationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsAttrAttrgroupRelationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsAttrAttrgroupRelation params) {
        iPmsAttrAttrgroupRelationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
