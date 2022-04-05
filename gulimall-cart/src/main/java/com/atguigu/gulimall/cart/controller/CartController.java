package com.atguigu.gulimall.cart.controller;

import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.CartItem;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author lxh
 * @createTime 2022-03-31 15:06:10
 */
@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/currentUserCartItems")
    @ResponseBody
    public List<CartItem> getUserCartItems() {
        return cartService.getUserCartItems();
    }

    /**
     * 浏览器cookie标识用户，user-key标识用户身份，一个月后过期
     * 如果第一次使用jd的购物车功能，都会给一个临时用户身份
     * 浏览器以后保存，每次访问都会带上这个cookie
     *
     * 登录：session有
     * 没登录：按照cookie里面的user-key来做
     *
     * @param session
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model) {
        var cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList.html";
    }

    /**
     * RedirectAttributes ra
     * ra.addFlashAttribute(); 将数据放在session里面，可以在页面取出，但只能使用一次
     * ra.addAttribute("skuId", skuId); 将数据放在url后面，多次使用
     * @param skuId
     * @param num
     * @param ra
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes ra) throws ExecutionException, InterruptedException {
        cartService.addToCart(skuId, num);
        ra.addAttribute("skuId", skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
//        重定向到成功页面，避免刷新页面，重复添加商品
        CartItem cartItem = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success.html";
    }
}
