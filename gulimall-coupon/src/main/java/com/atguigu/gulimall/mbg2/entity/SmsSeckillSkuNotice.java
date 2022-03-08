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
 * 秒杀商品通知订阅
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_seckill_sku_notice")
public class SmsSeckillSkuNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * member_id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * sku_id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 活动场次id
     */
    @TableField("session_id")
    private Long sessionId;

    /**
     * 订阅时间
     */
    @TableField("subcribe_time")
    private Date subcribeTime;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;

    /**
     * 通知方式[0-短信，1-邮件]
     */
    @TableField("notice_type")
    private Boolean noticeType;

}
