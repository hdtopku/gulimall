package com.atguigu.gulimall.wms.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-15 21:13:14
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
