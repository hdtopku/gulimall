package com.atguigu.gulimall.oms.web;

import com.atguigu.gulimall.oms.service.OrderService;
import com.atguigu.gulimall.oms.vo.OrderConfirmVo;
import com.atguigu.gulimall.oms.vo.OrderSubmitVo;
import com.atguigu.gulimall.oms.vo.SubmitOrderResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The type Order web controller.
 *
 * @author lxh
 * @createTime 2022 -04-05 00:32:49
 */
@Controller
@RequiredArgsConstructor
public class OrderWebController {
    private final OrderService orderService;

    /**
     * To trade string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/toTrade")
    public String toTrade(Model model) {
        OrderConfirmVo confirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", confirmVo);
        return "confirm";
    }

    @GetMapping("/buy")
    @ResponseBody
    public String buy() {
        orderService.buy(1L);
        return "ok";
    }

    /**
     * 下单功能
     *
     * @param submitVo the submit vo
     * @return the string
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo submitVo) {
        SubmitOrderResponseVo responseVo = orderService.submitOrder(submitVo);
//        下单：去创建订单，验证令牌，验证价格，锁库存
//        下单失败回到订单页重新确认下单信息
        if (responseVo.getCode() == 0) {
//            成功
            return "pay";
        } else {
            return "redirect:http://order.gulimall.com/toTrade";
        }
    }
    @Transactional
    public void a() {
        b();
        c();
        int i = 10 / 0;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void b() {

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void c() {

    }
}
