package com.atguigu.gulimall.oms.vo;

import com.atguigu.gulimall.oms.entity.OrderEntity;
import lombok.Data;

/**
 * @author lxh
 * @createTime 2022-04-06 15:18:06
 */
@Data
public class SubmitOrderResponseVo {
    private OrderEntity order;
    /**
     * 0 成功
     */
    private Integer code;
}
