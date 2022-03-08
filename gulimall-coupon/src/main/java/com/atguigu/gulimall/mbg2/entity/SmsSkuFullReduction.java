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
 * 商品满减信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_sku_full_reduction")
public class SmsSkuFullReduction implements Serializable {

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
     * 满多少
     */
    @TableField("full_price")
    private BigDecimal fullPrice;

    /**
     * 减多少
     */
    @TableField("reduce_price")
    private BigDecimal reducePrice;

    /**
     * 是否参与其他优惠
     */
    @TableField("add_other")
    private Boolean addOther;

}
