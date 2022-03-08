package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IOmsPaymentInfoService;
import com.atguigu.gulimall.mbg2.entity.OmsPaymentInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 支付信息表 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsPaymentInfo")
public class OmsPaymentInfoController {


    @Autowired
    private IOmsPaymentInfoService iOmsPaymentInfoService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsPaymentInfo>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsPaymentInfo> aPage = iOmsPaymentInfoService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsPaymentInfo> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsPaymentInfoService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsPaymentInfoService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsPaymentInfo params) {
        iOmsPaymentInfoService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
