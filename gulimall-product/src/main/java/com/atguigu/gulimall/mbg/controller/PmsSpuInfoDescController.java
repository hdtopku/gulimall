package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsSpuInfoDescService;
import com.atguigu.gulimall.mbg.entity.PmsSpuInfoDesc;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * spu信息介绍 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsSpuInfoDesc")
public class PmsSpuInfoDescController {


    @Autowired
    private IPmsSpuInfoDescService iPmsSpuInfoDescService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsSpuInfoDesc>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsSpuInfoDesc> aPage = iPmsSpuInfoDescService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsSpuInfoDesc> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsSpuInfoDescService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsSpuInfoDesc params) {
        iPmsSpuInfoDescService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsSpuInfoDescService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsSpuInfoDesc params) {
        iPmsSpuInfoDescService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
