package com.atguigu.gulimall.oms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The type Order confirm vo.
 *
 * @author lxh
 */
@Data
public class OrderConfirmVo {
    /**
     * 会有收货地址列表
     */
    private List<MemberAddressVo> address;
    /**
     * 所选的购物项
     */
    private List<OrderItemVo> items;
    /**
     * 优惠券信息
     */
    private Integer integration;

    /**
     * 防重令牌，防止重复提交订单
     */
    private String orderToken;

    /**
     * 获取订单总额
     * Gets total.
     *
     * @return the total
     */
    public BigDecimal getTotal() {
        return Optional.ofNullable(items).orElse(Collections.emptyList()).stream()
                .map(item-> item.getPrice().multiply(new BigDecimal(item.getCount())))
        .reduce(BigDecimal::add).orElse(new BigDecimal(0));
    }

    /**
     * 获取应付价格
     * Gets pay price.
     *
     * @return the pay price
     */
    public BigDecimal getPayPrice() {
        return getTotal();
    }

}
