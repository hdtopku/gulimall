package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsHomeAdvService;
import com.atguigu.gulimall.mbg2.entity.SmsHomeAdv;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 首页轮播广告 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsHomeAdv")
public class SmsHomeAdvController {


    @Autowired
    private ISmsHomeAdvService iSmsHomeAdvService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsHomeAdv>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsHomeAdv> aPage = iSmsHomeAdvService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsHomeAdv> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsHomeAdvService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsHomeAdv params) {
        iSmsHomeAdvService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsHomeAdvService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsHomeAdv params) {
        iSmsHomeAdvService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}