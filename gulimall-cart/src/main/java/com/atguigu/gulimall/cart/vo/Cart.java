package com.atguigu.gulimall.cart.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author lxh
 * @createTime 2022-03-31 16:28:29
 */
@Data
public class Cart {
    private List<CartItem> items;
//    商品总数量
    private Integer countNum;
//    商品类型数量
    private Integer countType;
//    商品总价
    private BigDecimal totalAmount;
//    减免总价
    private BigDecimal reduce = new BigDecimal(0);

    public Integer getCountNum() {
        return Optional.ofNullable(items).orElse(Collections.emptyList())
                .stream().mapToInt(CartItem::getCount).sum();
    }

    public BigDecimal getTotalAmount() {
        return Optional.ofNullable(items).orElse(Collections.emptyList())
                .stream().filter(CartItem::isChecked).map(CartItem::getTotalPrice).reduce(BigDecimal::add).orElse(new BigDecimal(0));
    }
}
