package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsAttrService;
import com.atguigu.gulimall.mbg2.entity.PmsAttr;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-attr")
public class PmsAttrAction {


    @Autowired
    private IPmsAttrService iPmsAttrService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsAttr>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsAttr> aPage = iPmsAttrService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsAttr> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsAttrService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsAttr params) {
        iPmsAttrService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsAttrService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsAttr params) {
        iPmsAttrService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
