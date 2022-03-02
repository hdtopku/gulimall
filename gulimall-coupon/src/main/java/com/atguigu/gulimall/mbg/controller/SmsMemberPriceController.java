package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsMemberPriceService;
import com.atguigu.gulimall.mbg.entity.SmsMemberPrice;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品会员价格 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsMemberPrice")
public class SmsMemberPriceController {


    @Autowired
    private ISmsMemberPriceService iSmsMemberPriceService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsMemberPrice>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsMemberPrice> aPage = iSmsMemberPriceService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsMemberPrice> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsMemberPriceService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsMemberPrice params) {
        iSmsMemberPriceService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsMemberPriceService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsMemberPrice params) {
        iSmsMemberPriceService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
