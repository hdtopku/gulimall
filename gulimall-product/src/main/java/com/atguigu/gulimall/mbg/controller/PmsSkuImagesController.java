package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsSkuImagesService;
import com.atguigu.gulimall.mbg.entity.PmsSkuImages;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * sku图片 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsSkuImages")
public class PmsSkuImagesController {


    @Autowired
    private IPmsSkuImagesService iPmsSkuImagesService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsSkuImages>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsSkuImages> aPage = iPmsSkuImagesService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsSkuImages> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsSkuImagesService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsSkuImages params) {
        iPmsSkuImagesService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsSkuImagesService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsSkuImages params) {
        iPmsSkuImagesService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
