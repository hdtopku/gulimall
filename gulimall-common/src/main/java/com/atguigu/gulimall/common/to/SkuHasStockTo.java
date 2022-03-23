package com.atguigu.gulimall.common.to;

import lombok.Data;

/**
 * @author lxh
 * @createTime 2022-03-18 22:28:27
 */
@Data
public class SkuHasStockTo {
    private Long skuId;
    private Boolean hasStock;
}
