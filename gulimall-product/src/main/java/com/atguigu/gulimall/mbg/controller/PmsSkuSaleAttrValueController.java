package com.atguigu.gulimall.mbg.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.atguigu.gulimall.mbg.service.IPmsSkuSaleAttrValueService;
import com.atguigu.gulimall.mbg.entity.PmsSkuSaleAttrValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * sku销售属性&值 前端控制器
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@RestController
@RequestMapping("/pmsSkuSaleAttrValue")
public class PmsSkuSaleAttrValueController {


    @Autowired
    private IPmsSkuSaleAttrValueService iPmsSkuSaleAttrValueService;

    @GetMapping(value = "/")
    public ResponseEntity<Page<PmsSkuSaleAttrValue>> list(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer pageSize) {
        if (current == null) {
            current = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Page<PmsSkuSaleAttrValue> aPage = iPmsSkuSaleAttrValueService.page(new Page<>(current, pageSize));
        return new ResponseEntity<>(aPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PmsSkuSaleAttrValue> getById(@PathVariable("id") String id) {
        return new ResponseEntity<>(iPmsSkuSaleAttrValueService.getById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody PmsSkuSaleAttrValue params) {
        iPmsSkuSaleAttrValueService.save(params);
        return new ResponseEntity<>("created successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        iPmsSkuSaleAttrValueService.removeById(id);
        return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> delete(@RequestBody PmsSkuSaleAttrValue params) {
        iPmsSkuSaleAttrValueService.updateById(params);
        return new ResponseEntity<>("updated successfully", HttpStatus.OK);
    }
}
