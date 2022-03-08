package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IWmsWareOrderTaskDetailService;
import com.atguigu.gulimall.mbg2.entity.WmsWareOrderTaskDetail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 库存工作单 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/wmsWareOrderTaskDetail")
public class WmsWareOrderTaskDetailController {


    @Autowired
    private IWmsWareOrderTaskDetailService iWmsWareOrderTaskDetailService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<WmsWareOrderTaskDetail>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<WmsWareOrderTaskDetail> aPage = iWmsWareOrderTaskDetailService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WmsWareOrderTaskDetail> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iWmsWareOrderTaskDetailService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iWmsWareOrderTaskDetailService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody WmsWareOrderTaskDetail params) {
        iWmsWareOrderTaskDetailService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
