package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsSkuLadderService;
import com.atguigu.gulimall.mbg2.entity.SmsSkuLadder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品阶梯价格 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsSkuLadder")
public class SmsSkuLadderController {


    @Autowired
    private ISmsSkuLadderService iSmsSkuLadderService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsSkuLadder>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsSkuLadder> aPage = iSmsSkuLadderService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsSkuLadder> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsSkuLadderService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsSkuLadder params) {
        iSmsSkuLadderService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsSkuLadderService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsSkuLadder params) {
        iSmsSkuLadderService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}