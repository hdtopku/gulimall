package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsSeckillSkuRelationService;
import com.atguigu.gulimall.mbg.entity.SmsSeckillSkuRelation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 秒杀活动商品关联 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsSeckillSkuRelation")
public class SmsSeckillSkuRelationController {


    @Autowired
    private ISmsSeckillSkuRelationService iSmsSeckillSkuRelationService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsSeckillSkuRelation>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsSeckillSkuRelation> aPage = iSmsSeckillSkuRelationService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsSeckillSkuRelation> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsSeckillSkuRelationService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsSeckillSkuRelation params) {
        iSmsSeckillSkuRelationService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsSeckillSkuRelationService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsSeckillSkuRelation params) {
        iSmsSeckillSkuRelationService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
