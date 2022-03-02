package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IWmsPurchaseService;
import com.atguigu.gulimall.mbg.entity.WmsPurchase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 采购信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/wmsPurchase")
public class WmsPurchaseController {


    @Autowired
    private IWmsPurchaseService iWmsPurchaseService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<WmsPurchase>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<WmsPurchase> aPage = iWmsPurchaseService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WmsPurchase> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iWmsPurchaseService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iWmsPurchaseService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody WmsPurchase params) {
        iWmsPurchaseService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
