package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IOmsOrderOperateHistoryService;
import com.atguigu.gulimall.mbg.entity.OmsOrderOperateHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单操作历史记录 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsOrderOperateHistory")
public class OmsOrderOperateHistoryController {


    @Autowired
    private IOmsOrderOperateHistoryService iOmsOrderOperateHistoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsOrderOperateHistory>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsOrderOperateHistory> aPage = iOmsOrderOperateHistoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsOrderOperateHistory> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsOrderOperateHistoryService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsOrderOperateHistoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsOrderOperateHistory params) {
        iOmsOrderOperateHistoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
