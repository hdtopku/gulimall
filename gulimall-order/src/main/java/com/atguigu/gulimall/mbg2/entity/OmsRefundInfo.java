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
 * 退款信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_refund_info")
public class OmsRefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 退款的订单
     */
    @TableField("order_return_id")
    private Long orderReturnId;

    /**
     * 退款金额
     */
    @TableField("refund")
    private BigDecimal refund;

    /**
     * 退款交易流水号
     */
    @TableField("refund_sn")
    private String refundSn;

    /**
     * 退款状态
     */
    @TableField("refund_status")
    private Boolean refundStatus;

    /**
     * 退款渠道[1-支付宝，2-微信，3-银联，4-汇款]
     */
    @TableField("refund_channel")
    private Integer refundChannel;

    @TableField("refund_content")
    private String refundContent;

}
