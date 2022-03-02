package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsAttrGroupService;
import com.atguigu.gulimall.mbg.entity.PmsAttrGroup;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsAttrGroup")
public class PmsAttrGroupController {


    @Autowired
    private IPmsAttrGroupService iPmsAttrGroupService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsAttrGroup>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsAttrGroup> aPage = iPmsAttrGroupService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsAttrGroup> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsAttrGroupService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsAttrGroup params) {
        iPmsAttrGroupService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsAttrGroupService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsAttrGroup params) {
        iPmsAttrGroupService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
