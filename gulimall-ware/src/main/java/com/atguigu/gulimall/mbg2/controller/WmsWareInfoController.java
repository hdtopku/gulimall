package com.atguigu.gulimall.mbg2.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg2.service.IWmsWareInfoService;
import com.atguigu.gulimall.mbg2.entity.WmsWareInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 仓库信息 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/wmsWareInfo")
public class WmsWareInfoController {


    @Autowired
    private IWmsWareInfoService iWmsWareInfoService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<WmsWareInfo>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<WmsWareInfo> aPage = iWmsWareInfoService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<WmsWareInfo> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iWmsWareInfoService.getById(id), HttpStatus.OK);
    }


    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iWmsWareInfoService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody WmsWareInfo params) {
        iWmsWareInfoService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
