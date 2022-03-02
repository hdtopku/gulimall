package com.atguigu.gulimall.mbg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_brand")
public class PmsBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;

    /**
     * 品牌名
     */
    @TableField("name")
    private String name;

    /**
     * 品牌logo地址
     */
    @TableField("logo")
    private String logo;

    /**
     * 介绍
     */
    @TableField("descript")
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    @TableField("show_status")
    private Integer showStatus;

    /**
     * 检索首字母
     */
    @TableField("first_letter")
    private String firstLetter;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

}
