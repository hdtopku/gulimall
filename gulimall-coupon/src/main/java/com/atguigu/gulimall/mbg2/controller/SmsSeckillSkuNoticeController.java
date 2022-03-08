package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.ISmsSeckillSkuNoticeService;
import com.atguigu.gulimall.mbg2.entity.SmsSeckillSkuNotice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 秒杀商品通知订阅 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsSeckillSkuNotice")
public class SmsSeckillSkuNoticeController {


    @Autowired
    private ISmsSeckillSkuNoticeService iSmsSeckillSkuNoticeService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsSeckillSkuNotice>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsSeckillSkuNotice> aPage = iSmsSeckillSkuNoticeService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsSeckillSkuNotice> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsSeckillSkuNoticeService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsSeckillSkuNotice params) {
        iSmsSeckillSkuNoticeService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsSeckillSkuNoticeService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsSeckillSkuNotice params) {
        iSmsSeckillSkuNoticeService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
