package com.atguigu.gulimall.oms.enums;

import lombok.Getter;

/**
 * The enum Order status enum.
 *
 * @author lxh
 * @createTime 2022 -04-06 16:02:05
 */
@Getter
public enum OrderStatusEnum {
    /**
     * Wait to payment order status enum.
     */
    CREATE_NEW(0,"待付款"),
    /**
     * Payment success order status enum.
     */
    PAYED(1, "付款成功"),
    /**
     * Sended order status enum.
     */
    SENDED(2, "已发货"),
    /**
     * Received order status enum.
     */
    RECEIVED(3, "已完成"),
    /**
     * Cancelled order status enum.
     */
    CANCELLED(4, "已取消"),
    /**
     * Servicing order status enum.
     */
    SERVICING(5, "售后中"),
    /**
     * Serviced order status enum.
     */
    SERVICED(6, "售后已完成")
    ;
    private Integer code;
    private String status;

    OrderStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

}
