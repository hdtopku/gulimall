package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsCouponSpuCategoryRelationService;
import com.atguigu.gulimall.mbg.entity.SmsCouponSpuCategoryRelation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 优惠券分类关联 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsCouponSpuCategoryRelation")
public class SmsCouponSpuCategoryRelationController {


    @Autowired
    private ISmsCouponSpuCategoryRelationService iSmsCouponSpuCategoryRelationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsCouponSpuCategoryRelation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsCouponSpuCategoryRelation> aPage = iSmsCouponSpuCategoryRelationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsCouponSpuCategoryRelation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsCouponSpuCategoryRelationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsCouponSpuCategoryRelation params) {
        iSmsCouponSpuCategoryRelationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsCouponSpuCategoryRelationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsCouponSpuCategoryRelation params) {
        iSmsCouponSpuCategoryRelationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
