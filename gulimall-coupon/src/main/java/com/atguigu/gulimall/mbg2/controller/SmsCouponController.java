package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsCouponService;
import com.atguigu.gulimall.mbg2.entity.SmsCoupon;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsCoupon")
public class SmsCouponController {


    @Autowired
    private ISmsCouponService iSmsCouponService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsCoupon>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsCoupon> aPage = iSmsCouponService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsCoupon> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsCouponService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsCoupon params) {
        iSmsCouponService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsCouponService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsCoupon params) {
        iSmsCouponService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}