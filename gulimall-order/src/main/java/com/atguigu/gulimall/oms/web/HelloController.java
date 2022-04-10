package com.atguigu.gulimall.oms.web;

import cn.hutool.core.util.IdUtil;
import com.atguigu.gulimall.oms.entity.OrderEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lxh
 * @createTime 2022-04-04 18:38:59
 */
@Controller
public class HelloController {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @GetMapping("/{page}.html")
    public String listPage(@PathVariable("page") String page){
        return page;
    }
    @GetMapping("/createOrder")
    @ResponseBody
    public String createOrder() {
        OrderEntity entity = new OrderEntity();
        entity.setOrderSn(IdUtil.simpleUUID());
        entity.setModifyTime(new Date());
        rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", entity);
        return "ok";
    }
}
