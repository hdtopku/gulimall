package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IUmsIntegrationChangeHistoryService;
import com.atguigu.gulimall.mbg2.entity.UmsIntegrationChangeHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 积分变化历史记录 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsIntegrationChangeHistory")
public class UmsIntegrationChangeHistoryController {


    @Autowired
    private IUmsIntegrationChangeHistoryService iUmsIntegrationChangeHistoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsIntegrationChangeHistory>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsIntegrationChangeHistory> aPage = iUmsIntegrationChangeHistoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsIntegrationChangeHistory> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsIntegrationChangeHistoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsIntegrationChangeHistory params) {
        iUmsIntegrationChangeHistoryService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsIntegrationChangeHistoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsIntegrationChangeHistory params) {
        iUmsIntegrationChangeHistoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
