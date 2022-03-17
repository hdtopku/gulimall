package com.atguigu.gulimall.wms.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-15 23:48:36
 */
@Data
public class PurchaseDoneVo {
    private Long id;
    private List<PurchaseDoneItemVo> items;

}
