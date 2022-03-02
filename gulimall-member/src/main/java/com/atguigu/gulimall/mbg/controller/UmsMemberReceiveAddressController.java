package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IUmsMemberReceiveAddressService;
import com.atguigu.gulimall.mbg.entity.UmsMemberReceiveAddress;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员收货地址 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMemberReceiveAddress")
public class UmsMemberReceiveAddressController {


    @Autowired
    private IUmsMemberReceiveAddressService iUmsMemberReceiveAddressService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMemberReceiveAddress>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMemberReceiveAddress> aPage = iUmsMemberReceiveAddressService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMemberReceiveAddress> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberReceiveAddressService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMemberReceiveAddress params) {
        iUmsMemberReceiveAddressService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberReceiveAddressService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMemberReceiveAddress params) {
        iUmsMemberReceiveAddressService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
