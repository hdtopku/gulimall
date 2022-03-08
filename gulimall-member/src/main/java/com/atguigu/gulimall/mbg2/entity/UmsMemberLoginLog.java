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
 * 会员登录记录
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_login_log")
public class UmsMemberLoginLog implements Serializable {

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
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * city
     */
    @TableField("city")
    private String city;

    /**
     * 登录类型[1-web，2-app]
     */
    @TableField("login_type")
    private Boolean loginType;

}
