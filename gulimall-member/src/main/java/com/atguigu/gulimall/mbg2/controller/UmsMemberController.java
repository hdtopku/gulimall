package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IUmsMemberService;
import com.atguigu.gulimall.mbg2.entity.UmsMember;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMember")
public class UmsMemberController {


    @Autowired
    private IUmsMemberService iUmsMemberService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMember>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMember> aPage = iUmsMemberService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMember> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMember params) {
        iUmsMemberService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMember params) {
        iUmsMemberService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
