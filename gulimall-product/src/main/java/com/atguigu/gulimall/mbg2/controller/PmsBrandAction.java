package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsBrandService;
import com.atguigu.gulimall.mbg2.entity.PmsBrand;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-brand")
public class PmsBrandAction {


    @Autowired
    private IPmsBrandService iPmsBrandService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsBrand>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsBrand> aPage = iPmsBrandService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsBrand> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsBrandService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsBrand params) {
        iPmsBrandService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsBrandService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsBrand params) {
        iPmsBrandService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
