package com.atguigu.gulimall.mbg2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠券领取历史记录
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_coupon_history")
public class SmsCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠券id
     */
    @TableField("coupon_id")
    private Long couponId;

    /**
     * 会员id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 会员名字
     */
    @TableField("member_nick_name")
    private String memberNickName;

    /**
     * 获取方式[0->后台赠送；1->主动领取]
     */
    @TableField("get_type")
    private Boolean getType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 使用状态[0->未使用；1->已使用；2->已过期]
     */
    @TableField("use_type")
    private Boolean useType;

    /**
     * 使用时间
     */
    @TableField("use_time")
    private Date useTime;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * 订单号
     */
    @TableField("order_sn")
    private Long orderSn;

}
