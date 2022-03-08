package com.atguigu.gulimall.mbg2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 优惠券分类关联
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_coupon_spu_category_relation")
public class SmsCouponSpuCategoryRelation implements Serializable {

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
     * 产品分类id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 产品分类名称
     */
    @TableField("category_name")
    private String categoryName;

}
