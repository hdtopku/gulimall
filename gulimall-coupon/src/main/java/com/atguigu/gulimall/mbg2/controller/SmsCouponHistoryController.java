package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsCouponHistoryService;
import com.atguigu.gulimall.mbg2.entity.SmsCouponHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 优惠券领取历史记录 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsCouponHistory")
public class SmsCouponHistoryController {


    @Autowired
    private ISmsCouponHistoryService iSmsCouponHistoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsCouponHistory>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsCouponHistory> aPage = iSmsCouponHistoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsCouponHistory> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsCouponHistoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsCouponHistory params) {
        iSmsCouponHistoryService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsCouponHistoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsCouponHistory params) {
        iSmsCouponHistoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
