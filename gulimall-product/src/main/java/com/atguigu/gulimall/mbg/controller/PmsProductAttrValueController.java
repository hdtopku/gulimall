package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsProductAttrValueService;
import com.atguigu.gulimall.mbg.entity.PmsProductAttrValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * spu属性值 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsProductAttrValue")
public class PmsProductAttrValueController {


    @Autowired
    private IPmsProductAttrValueService iPmsProductAttrValueService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsProductAttrValue>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsProductAttrValue> aPage = iPmsProductAttrValueService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsProductAttrValue> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsProductAttrValueService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsProductAttrValue params) {
        iPmsProductAttrValueService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsProductAttrValueService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsProductAttrValue params) {
        iPmsProductAttrValueService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
