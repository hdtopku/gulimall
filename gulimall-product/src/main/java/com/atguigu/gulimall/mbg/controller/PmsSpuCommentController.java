package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsSpuCommentService;
import com.atguigu.gulimall.mbg.entity.PmsSpuComment;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsSpuComment")
public class PmsSpuCommentController {


    @Autowired
    private IPmsSpuCommentService iPmsSpuCommentService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsSpuComment>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsSpuComment> aPage = iPmsSpuCommentService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsSpuComment> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsSpuCommentService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsSpuComment params) {
        iPmsSpuCommentService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsSpuCommentService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsSpuComment params) {
        iPmsSpuCommentService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
