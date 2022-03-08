package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IUmsMemberLoginLogService;
import com.atguigu.gulimall.mbg2.entity.UmsMemberLoginLog;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员登录记录 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMemberLoginLog")
public class UmsMemberLoginLogController {


    @Autowired
    private IUmsMemberLoginLogService iUmsMemberLoginLogService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMemberLoginLog>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMemberLoginLog> aPage = iUmsMemberLoginLogService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMemberLoginLog> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberLoginLogService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMemberLoginLog params) {
        iUmsMemberLoginLogService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberLoginLogService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMemberLoginLog params) {
        iUmsMemberLoginLogService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}