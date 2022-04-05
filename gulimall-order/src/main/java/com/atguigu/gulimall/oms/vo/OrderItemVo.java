package com.atguigu.gulimall.oms.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-04-05 12:36:39
 */
@Data
public class OrderItemVo {
    private Long skuId;
    private boolean checked = true;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;
}
