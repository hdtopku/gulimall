package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsHomeSubjectSpuService;
import com.atguigu.gulimall.mbg.entity.SmsHomeSubjectSpu;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 专题商品 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsHomeSubjectSpu")
public class SmsHomeSubjectSpuController {


    @Autowired
    private ISmsHomeSubjectSpuService iSmsHomeSubjectSpuService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsHomeSubjectSpu>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsHomeSubjectSpu> aPage = iSmsHomeSubjectSpuService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsHomeSubjectSpu> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsHomeSubjectSpuService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsHomeSubjectSpu params) {
        iSmsHomeSubjectSpuService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsHomeSubjectSpuService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsHomeSubjectSpu params) {
        iSmsHomeSubjectSpuService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
