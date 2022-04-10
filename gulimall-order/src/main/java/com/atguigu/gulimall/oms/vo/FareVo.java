package com.atguigu.gulimall.oms.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lxh
 * @createTime 2022-04-06 16:26:10
 */
@Data
public class FareVo {
    private MemberAddressVo memberAddressVo;
    private BigDecimal fare;
}
