package com.atguigu.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-14 11:30:07
 */
@Data
public class SkuReduceTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
