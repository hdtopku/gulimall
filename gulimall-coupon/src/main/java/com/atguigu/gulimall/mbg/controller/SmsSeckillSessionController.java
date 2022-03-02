package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.ISmsSeckillSessionService;
import com.atguigu.gulimall.mbg.entity.SmsSeckillSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 秒杀活动场次 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/smsSeckillSession")
public class SmsSeckillSessionController {


    @Autowired
    private ISmsSeckillSessionService iSmsSeckillSessionService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<SmsSeckillSession>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<SmsSeckillSession> aPage = iSmsSeckillSessionService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SmsSeckillSession> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iSmsSeckillSessionService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody SmsSeckillSession params) {
        iSmsSeckillSessionService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iSmsSeckillSessionService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody SmsSeckillSession params) {
        iSmsSeckillSessionService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
