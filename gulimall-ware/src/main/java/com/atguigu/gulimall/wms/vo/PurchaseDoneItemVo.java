package com.atguigu.gulimall.wms.vo;

import lombok.Data;

/**
 * @author lxh
 * @createTime 2022-03-16 00:03:00
 */
@Data
public class PurchaseDoneItemVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
