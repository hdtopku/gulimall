package com.atguigu.gulimall.mbg2.entity;

import java.math.BigDecimal;

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
 * 优惠券信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_coupon")
public class SmsCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]
     */
    @TableField("coupon_type")
    private Boolean couponType;

    /**
     * 优惠券图片
     */
    @TableField("coupon_img")
    private String couponImg;

    /**
     * 优惠卷名字
     */
    @TableField("coupon_name")
    private String couponName;

    /**
     * 数量
     */
    @TableField("num")
    private Integer num;

    /**
     * 金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 每人限领张数
     */
    @TableField("per_limit")
    private Integer perLimit;

    /**
     * 使用门槛
     */
    @TableField("min_point")
    private BigDecimal minPoint;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 使用类型[0->全场通用；1->指定分类；2->指定商品]
     */
    @TableField("use_type")
    private Boolean useType;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 发行数量
     */
    @TableField("publish_count")
    private Integer publishCount;

    /**
     * 已使用数量
     */
    @TableField("use_count")
    private Integer useCount;

    /**
     * 领取数量
     */
    @TableField("receive_count")
    private Integer receiveCount;

    /**
     * 可以领取的开始日期
     */
    @TableField("enable_start_time")
    private Date enableStartTime;

    /**
     * 可以领取的结束日期
     */
    @TableField("enable_end_time")
    private Date enableEndTime;

    /**
     * 优惠码
     */
    @TableField("code")
    private String code;

    /**
     * 可以领取的会员等级[0->不限等级，其他-对应等级]
     */
    @TableField("member_level")
    private Boolean memberLevel;

    /**
     * 发布状态[0-未发布，1-已发布]
     */
    @TableField("publish")
    private Boolean publish;

}
