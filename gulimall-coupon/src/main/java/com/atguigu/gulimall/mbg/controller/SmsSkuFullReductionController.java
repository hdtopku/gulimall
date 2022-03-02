package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsSkuFullReductionService;
import com.atguigu.gulimall.mbg.entity.SmsSkuFullReduction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品满减信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsSkuFullReduction")
public class SmsSkuFullReductionController {


    @Autowired
    private ISmsSkuFullReductionService iSmsSkuFullReductionService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsSkuFullReduction>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsSkuFullReduction> aPage = iSmsSkuFullReductionService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsSkuFullReduction> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsSkuFullReductionService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsSkuFullReduction params) {
        iSmsSkuFullReductionService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsSkuFullReductionService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsSkuFullReduction params) {
        iSmsSkuFullReductionService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
