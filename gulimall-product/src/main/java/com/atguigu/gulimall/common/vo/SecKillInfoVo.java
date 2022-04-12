package com.atguigu.gulimall.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lxh
 * @createTime 2022-04-11 20:22:28
 */
@Data
public class SecKillInfoVo {

    /**
     * id
     */
    private Long id;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;

    /**商品秒杀随机码*/
    private String randomCoder;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
    /**
     * 当前商品秒杀的开始时间
     */
    private Long startTime;
    /**
     * 当前商品秒杀结束时间
     */
    private Long endTime;

}
