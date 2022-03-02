package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IUmsMemberLevelService;
import com.atguigu.gulimall.mbg.entity.UmsMemberLevel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员等级 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMemberLevel")
public class UmsMemberLevelController {


    @Autowired
    private IUmsMemberLevelService iUmsMemberLevelService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMemberLevel>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMemberLevel> aPage = iUmsMemberLevelService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMemberLevel> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberLevelService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMemberLevel params) {
        iUmsMemberLevelService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberLevelService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMemberLevel params) {
        iUmsMemberLevelService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
