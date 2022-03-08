package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IPmsCommentReplayService;
import com.atguigu.gulimall.mbg2.entity.PmsCommentReplay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品评价回复关系 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@RestController
@RequestMapping("/pms-comment-replay")
public class PmsCommentReplayAction {


    @Autowired
    private IPmsCommentReplayService iPmsCommentReplayService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsCommentReplay>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsCommentReplay> aPage = iPmsCommentReplayService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsCommentReplay> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsCommentReplayService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsCommentReplay params) {
        iPmsCommentReplayService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsCommentReplayService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsCommentReplay params) {
        iPmsCommentReplayService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
