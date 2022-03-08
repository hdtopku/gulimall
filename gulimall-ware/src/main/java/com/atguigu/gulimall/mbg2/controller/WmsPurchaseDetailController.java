package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IWmsPurchaseDetailService;
import com.atguigu.gulimall.mbg2.entity.WmsPurchaseDetail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/wmsPurchaseDetail")
public class WmsPurchaseDetailController {


    @Autowired
    private IWmsPurchaseDetailService iWmsPurchaseDetailService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<WmsPurchaseDetail>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<WmsPurchaseDetail> aPage = iWmsPurchaseDetailService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WmsPurchaseDetail> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iWmsPurchaseDetailService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iWmsPurchaseDetailService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody WmsPurchaseDetail params) {
        iWmsPurchaseDetailService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
