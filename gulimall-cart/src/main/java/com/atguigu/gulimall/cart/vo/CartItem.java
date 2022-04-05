package com.atguigu.gulimall.cart.vo;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-31 16:28:24
 */
@Data
public class CartItem {
    private Long skuId;
    private boolean checked = true;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;

    /**
     * 计算总价
     * @return
     */
    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(this.count));
    }
}
