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
 * 积分变化历史记录
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_integration_change_history")
public class UmsIntegrationChangeHistory implements Serializable {

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
     * create_time
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 变化的值
     */
    @TableField("change_count")
    private Integer changeCount;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 来源[0->购物；1->管理员修改;2->活动]
     */
    @TableField("source_tyoe")
    private Integer sourceTyoe;

}
