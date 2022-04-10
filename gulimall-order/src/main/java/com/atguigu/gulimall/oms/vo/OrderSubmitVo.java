package com.atguigu.gulimall.oms.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lxh
 * @createTime 2022-04-06 14:45:05
 */
@Data
public class OrderSubmitVo {
    private Long addrId;
    /**
     * 支付类型
     */
    private Integer payType;
    /**
     * 防重令牌
     */
    private String orderToken;
    /**
     * 支付金额
     */
    private BigDecimal payPrice;
    /**
     * 备注
     */
    private String note;
//    用户信息到session中去取，无需提交
}
