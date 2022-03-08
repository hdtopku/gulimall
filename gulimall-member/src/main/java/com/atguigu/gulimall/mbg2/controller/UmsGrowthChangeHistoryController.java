package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IUmsGrowthChangeHistoryService;
import com.atguigu.gulimall.mbg2.entity.UmsGrowthChangeHistory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 成长值变化历史记录 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsGrowthChangeHistory")
public class UmsGrowthChangeHistoryController {


    @Autowired
    private IUmsGrowthChangeHistoryService iUmsGrowthChangeHistoryService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsGrowthChangeHistory>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsGrowthChangeHistory> aPage = iUmsGrowthChangeHistoryService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsGrowthChangeHistory> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsGrowthChangeHistoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsGrowthChangeHistory params) {
        iUmsGrowthChangeHistoryService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsGrowthChangeHistoryService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsGrowthChangeHistory params) {
        iUmsGrowthChangeHistoryService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
