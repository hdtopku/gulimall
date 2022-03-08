package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsSpuImagesService;
import com.atguigu.gulimall.mbg2.entity.PmsSpuImages;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * spu图片 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-spu-images")
public class PmsSpuImagesAction {


    @Autowired
    private IPmsSpuImagesService iPmsSpuImagesService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsSpuImages>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsSpuImages> aPage = iPmsSpuImagesService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsSpuImages> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsSpuImagesService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsSpuImages params) {
        iPmsSpuImagesService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsSpuImagesService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsSpuImages params) {
        iPmsSpuImagesService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
