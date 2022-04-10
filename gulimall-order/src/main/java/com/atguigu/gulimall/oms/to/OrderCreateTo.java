package com.atguigu.gulimall.oms.to;

import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.atguigu.gulimall.oms.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-04-06 15:56:13
 */
@Data
public class OrderCreateTo {
    private OrderEntity order;
    private List<OrderItemEntity> items;
    /**
     * 应付价格
     */
    private BigDecimal payPrice;
    /**
     * 运费
     */
    private BigDecimal fare;
}
