package com.atguigu.gulimall;

import cn.hutool.core.collection.ListUtil;
import com.atguigu.gulimall.cart.vo.Cart;
import com.atguigu.gulimall.cart.vo.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallCartApplicationTests {

    @Test
    void contextLoads() {
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setCount(12);
        cart.setItems(ListUtil.of(cartItem));
        cart.setItems(null);

    }

}
