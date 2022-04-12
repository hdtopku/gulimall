package com.atguigu.gulimall.web;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.gulimall.pms.service.SkuInfoService;
import com.atguigu.gulimall.common.vo.SkuItemVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.ExecutionException;

/**
 * @author lxh
 * @createTime 2022-03-28 18:47:09
 */
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final SkuInfoService skuInfoService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws ExecutionException, InterruptedException, BlockException {
        SkuItemVo skuItemVo = skuInfoService.item(skuId);
        model.addAttribute("item", skuItemVo);
        return "item";
    }
}
