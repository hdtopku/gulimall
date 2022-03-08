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
 * 首页轮播广告
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_home_adv")
public class SmsHomeAdv implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 图片地址
     */
    @TableField("pic")
    private String pic;

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
     * 状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * 点击数
     */
    @TableField("click_count")
    private Integer clickCount;

    /**
     * 广告详情连接地址
     */
    @TableField("url")
    private String url;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 发布者
     */
    @TableField("publisher_id")
    private Long publisherId;

    /**
     * 审核者
     */
    @TableField("auth_id")
    private Long authId;

}
