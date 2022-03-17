package com.atguigu.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lxh
 * @createTime 2022-03-14 10:54:38
 */
@Data
public class SpuBoundsTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
