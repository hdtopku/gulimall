package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IOmsOrderSettingService;
import com.atguigu.gulimall.mbg.entity.OmsOrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单配置信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/omsOrderSetting")
public class OmsOrderSettingController {


    @Autowired
    private IOmsOrderSettingService iOmsOrderSettingService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<OmsOrderSetting>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<OmsOrderSetting> aPage = iOmsOrderSettingService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OmsOrderSetting> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iOmsOrderSettingService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iOmsOrderSettingService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody OmsOrderSetting params) {
        iOmsOrderSettingService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
