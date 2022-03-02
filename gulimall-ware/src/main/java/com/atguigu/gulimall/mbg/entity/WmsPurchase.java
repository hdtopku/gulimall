package com.atguigu.gulimall.mbg.entity;

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
 * 采购信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wms_purchase")
public class WmsPurchase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 采购人id
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 采购人名
     */
    @TableField("assignee_name")
    private String assigneeName;

    /**
     * 联系方式
     */
    @TableField("phone")
    private String phone;

    /**
     * 优先级
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 仓库id
     */
    @TableField("ware_id")
    private Long wareId;

    /**
     * 总金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新日期
     */
    @TableField("update_time")
    private Date updateTime;

}
