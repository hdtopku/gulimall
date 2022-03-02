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
 *
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_purchase_detail")
public class WmsPurchaseDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 采购单id
     */
    @TableField("purchase_id")
    private Long purchaseId;

    /**
     * 采购商品id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 采购数量
     */
    @TableField("sku_num")
    private Integer skuNum;

    /**
     * 采购金额
     */
    @TableField("sku_price")
    private BigDecimal skuPrice;

    /**
     * 仓库id
     */
    @TableField("ware_id")
    private Long wareId;

    /**
     * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
     */
    @TableField("status")
    private Integer status;

}
