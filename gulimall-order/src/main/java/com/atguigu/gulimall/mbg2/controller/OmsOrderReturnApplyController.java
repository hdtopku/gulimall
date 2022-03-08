package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IOmsOrderReturnApplyService;
import com.atguigu.gulimall.mbg2.entity.OmsOrderReturnApply;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsOrderReturnApply")
public class OmsOrderReturnApplyController {


    @Autowired
    private IOmsOrderReturnApplyService iOmsOrderReturnApplyService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsOrderReturnApply>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsOrderReturnApply> aPage = iOmsOrderReturnApplyService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsOrderReturnApply> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsOrderReturnApplyService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsOrderReturnApplyService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsOrderReturnApply params) {
        iOmsOrderReturnApplyService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
