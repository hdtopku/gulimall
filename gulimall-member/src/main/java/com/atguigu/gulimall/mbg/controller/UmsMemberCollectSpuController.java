package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IUmsMemberCollectSpuService;
import com.atguigu.gulimall.mbg.entity.UmsMemberCollectSpu;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员收藏的商品 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/umsMemberCollectSpu")
public class UmsMemberCollectSpuController {


    @Autowired
    private IUmsMemberCollectSpuService iUmsMemberCollectSpuService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<UmsMemberCollectSpu>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<UmsMemberCollectSpu> aPage = iUmsMemberCollectSpuService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UmsMemberCollectSpu> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iUmsMemberCollectSpuService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody UmsMemberCollectSpu params) {
        iUmsMemberCollectSpuService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iUmsMemberCollectSpuService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody UmsMemberCollectSpu params) {
        iUmsMemberCollectSpuService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
