package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsHomeSubjectService;
import com.atguigu.gulimall.mbg.entity.SmsHomeSubject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsHomeSubject")
public class SmsHomeSubjectController {


    @Autowired
    private ISmsHomeSubjectService iSmsHomeSubjectService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsHomeSubject>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsHomeSubject> aPage = iSmsHomeSubjectService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsHomeSubject> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsHomeSubjectService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsHomeSubject params) {
        iSmsHomeSubjectService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsHomeSubjectService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsHomeSubject params) {
        iSmsHomeSubjectService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
