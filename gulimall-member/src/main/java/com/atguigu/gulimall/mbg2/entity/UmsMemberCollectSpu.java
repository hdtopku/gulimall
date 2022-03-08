package com.atguigu.gulimall.mbg2.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员收藏的商品
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_collect_spu")
public class UmsMemberCollectSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 会员id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * spu_id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * spu_name
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * spu_img
     */
    @TableField("spu_img")
    private String spuImg;

    /**
     * create_time
     */
    @TableField("create_time")
    private Date createTime;

}
