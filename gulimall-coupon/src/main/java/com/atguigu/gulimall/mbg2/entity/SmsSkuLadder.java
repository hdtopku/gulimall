package com.atguigu.gulimall.mbg2.entity;

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
 * 商品阶梯价格
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_sku_ladder")
public class SmsSkuLadder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu_id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 满几件
     */
    @TableField("full_count")
    private Integer fullCount;

    /**
     * 打几折
     */
    @TableField("discount")
    private BigDecimal discount;

    /**
     * 折后价
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 是否叠加其他优惠[0-不可叠加，1-可叠加]
     */
    @TableField("add_other")
    private Boolean addOther;

}
