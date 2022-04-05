package com.atguigu.gulimall.oms.web;

import com.atguigu.gulimall.oms.service.OrderService;
import com.atguigu.gulimall.oms.vo.OrderConfirmVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lxh
 * @createTime 2022-04-05 00:32:49
 */
@Controller
@RequiredArgsConstructor
public class OrderWebController {
    private final OrderService orderService;
    @GetMapping("/toTrade")
    public String toTrade(Model model) {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", confirmVo);
        return "confirm";
    }
}
