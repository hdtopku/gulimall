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
 * 秒杀活动场次
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_seckill_session")
public class SmsSeckillSession implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 场次名称
     */
    @TableField("name")
    private String name;

    /**
     * 每日开始时间
     */
    @TableField("start_time")
    private Date startTime;

    /**
     * 每日结束时间
     */
    @TableField("end_time")
    private Date endTime;

    /**
     * 启用状态
     */
    @TableField("status")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
