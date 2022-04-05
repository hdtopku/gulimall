package com.atguigu.gulimall.oms.feign;

import com.atguigu.gulimall.oms.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * The interface Cart feign service.
 */
@FeignClient("gulimall-cart")
public interface CartFeignService {
    /**
     * Gets cart items.
     *
     * @return the cart items
     */
    @GetMapping("/currentUserCartItems")
    @ResponseBody
    List<OrderItemVo> getUserCartItems();
}
