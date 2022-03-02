package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IOmsOrderService;
import com.atguigu.gulimall.mbg.entity.OmsOrder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsOrder")
public class OmsOrderController {


    @Autowired
    private IOmsOrderService iOmsOrderService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsOrder>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsOrder> aPage = iOmsOrderService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsOrder> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsOrderService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsOrderService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsOrder params) {
        iOmsOrderService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
