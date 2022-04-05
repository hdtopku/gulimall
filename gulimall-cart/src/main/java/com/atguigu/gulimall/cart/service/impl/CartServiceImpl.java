package com.atguigu.gulimall.cart.service.impl;

/**
 * @author lxh
 * @createTime 2022-03-31 17:33:27
 */

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.cart.feign.ProductFeignService;
import com.atguigu.gulimall.cart.interceptor.CartInterceptor;
import com.atguigu.gulimall.cart.service.CartService;
import com.atguigu.gulimall.cart.vo.Cart;
import com.atguigu.gulimall.cart.vo.CartItem;
import com.atguigu.gulimall.cart.vo.SkuInfoVo;
import com.atguigu.gulimall.common.to.UserInfoTo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final StringRedisTemplate redisTemplate;
    private final ProductFeignService productFeignService;
    private final ThreadPoolExecutor executor;

    private static final String CART_PREFIX = "gulimall:cart:";

    @Override
    public CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException {
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        CartItem cartItem = new CartItem();
        String res = (String) cartOps.get(skuId.toString());
        if (StrUtil.isBlank(res)) {
//        远程查询当前要添加的商品信息
            CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
                R data = productFeignService.getSkuInfo(skuId);
                SkuInfoVo skuInfo = data.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                cartItem.setSkuId(skuId);
                cartItem.setChecked(true);
                cartItem.setCount(1);
                cartItem.setImage(skuInfo.getSkuDefaultImg());
                cartItem.setTitle(skuInfo.getSkuTitle());
                cartItem.setPrice(skuInfo.getPrice());
            }, executor);
//        远程查询sku组合信息
            CompletableFuture<Void> attrFuture = CompletableFuture.runAsync(() -> {
                List<String> skuSaleAttrValues = productFeignService.getSkuSaleAttrValues(skuId);
                cartItem.setSkuAttr(skuSaleAttrValues);
            });
            CompletableFuture.allOf(skuInfoFuture, attrFuture).get();
            cartOps.put(String.valueOf(skuId), JSONUtil.toJsonStr(cartItem));
            return cartItem;
        }
//      购物车有此商品，修改数量
        CartItem item = JSONUtil.toBean(res, CartItem.class);
        item.setCount(item.getCount()+num);
        cartOps.put(String.valueOf(skuId), JSONUtil.toJsonStr(item));
        return item;
    }

    @Override
    public CartItem getCartItem(Long skuId) {

        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        String o = (String) cartOps.get(skuId.toString());
        return JSONUtil.toBean(o, CartItem.class);
    }

    @Override
    public Cart getCart() {
        Cart cart = new Cart();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        BoundHashOperations<String, Object, Object> cartOps = getCartOps();
        List<CartItem> collect = Optional.ofNullable(cartOps.values()).orElse(Collections.emptyList()).stream().map(item ->
                JSONUtil.toBean((String) item, CartItem.class)).collect(Collectors.toList());
        cart.setItems(collect);
        if (null != userInfoTo.getUserId()) {
//            TODO 已登录，合并购物车
        }
        return cart;
    }

    private BoundHashOperations<String, Object, Object> getCartOps() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        String cartKey = "";
        if (null != userInfoTo.getUserId()) {
            cartKey = CART_PREFIX + userInfoTo.getUserId();
        } else {
            cartKey = CART_PREFIX + userInfoTo.getUserKey();
        }
        return redisTemplate.boundHashOps(cartKey);
    }

    @Override
    public void clearCart(String cartKey) {
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartItem> getUserCartItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (null != userInfoTo.getUserId()) {
            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            return getCartItems(cartKey).stream().filter(item -> item.isChecked()).map(item -> {
                item.setPrice(productFeignService.getPrice(item.getSkuId()));
                return item;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public List<CartItem> getCartItems(String cartKey) {
        return new ArrayList<>();
    }
}
