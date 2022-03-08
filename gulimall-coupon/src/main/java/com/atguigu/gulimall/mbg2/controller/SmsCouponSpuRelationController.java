package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsCouponSpuRelationService;
import com.atguigu.gulimall.mbg2.entity.SmsCouponSpuRelation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 优惠券与产品关联 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsCouponSpuRelation")
public class SmsCouponSpuRelationController {


    @Autowired
    private ISmsCouponSpuRelationService iSmsCouponSpuRelationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsCouponSpuRelation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsCouponSpuRelation> aPage = iSmsCouponSpuRelationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsCouponSpuRelation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsCouponSpuRelationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsCouponSpuRelation params) {
        iSmsCouponSpuRelationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsCouponSpuRelationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsCouponSpuRelation params) {
        iSmsCouponSpuRelationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}