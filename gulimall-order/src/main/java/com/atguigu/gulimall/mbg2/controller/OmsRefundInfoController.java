package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IOmsRefundInfoService;
import com.atguigu.gulimall.mbg2.entity.OmsRefundInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 退款信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsRefundInfo")
public class OmsRefundInfoController {


    @Autowired
    private IOmsRefundInfoService iOmsRefundInfoService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsRefundInfo>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsRefundInfo> aPage = iOmsRefundInfoService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsRefundInfo> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsRefundInfoService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsRefundInfoService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsRefundInfo params) {
        iOmsRefundInfoService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
