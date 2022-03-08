package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IUmsMemberStatisticsInfoService;
import com.atguigu.gulimall.mbg2.entity.UmsMemberStatisticsInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员统计信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMemberStatisticsInfo")
public class UmsMemberStatisticsInfoController {


    @Autowired
    private IUmsMemberStatisticsInfoService iUmsMemberStatisticsInfoService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMemberStatisticsInfo>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMemberStatisticsInfo> aPage = iUmsMemberStatisticsInfoService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMemberStatisticsInfo> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberStatisticsInfoService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMemberStatisticsInfo params) {
        iUmsMemberStatisticsInfoService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberStatisticsInfoService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMemberStatisticsInfo params) {
        iUmsMemberStatisticsInfoService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
