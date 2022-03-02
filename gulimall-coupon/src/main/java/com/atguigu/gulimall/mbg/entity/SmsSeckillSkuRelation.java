package com.atguigu.gulimall.mbg.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀活动商品关联
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_seckill_sku_relation")
public class SmsSeckillSkuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动id
     */
    @TableField("promotion_id")
    private Long promotionId;

    /**
     * 活动场次id
     */
    @TableField("promotion_session_id")
    private Long promotionSessionId;

    /**
     * 商品id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 秒杀价格
     */
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    /**
     * 秒杀总量
     */
    @TableField("seckill_count")
    private BigDecimal seckillCount;

    /**
     * 每人限购数量
     */
    @TableField("seckill_limit")
    private BigDecimal seckillLimit;

    /**
     * 排序
     */
    @TableField("seckill_sort")
    private Integer seckillSort;

}
