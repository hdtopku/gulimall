package com.atguigu.gulimall.mbg2.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单项信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_item")
public class OmsOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * order_id
     */
    @TableField("order_id")
    private Long orderId;

    /**
     * order_sn
     */
    @TableField("order_sn")
    private String orderSn;

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
     * spu_pic
     */
    @TableField("spu_pic")
    private String spuPic;

    /**
     * 品牌
     */
    @TableField("spu_brand")
    private String spuBrand;

    /**
     * 商品分类id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 商品sku编号
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 商品sku名字
     */
    @TableField("sku_name")
    private String skuName;

    /**
     * 商品sku图片
     */
    @TableField("sku_pic")
    private String skuPic;

    /**
     * 商品sku价格
     */
    @TableField("sku_price")
    private BigDecimal skuPrice;

    /**
     * 商品购买的数量
     */
    @TableField("sku_quantity")
    private Integer skuQuantity;

    /**
     * 商品销售属性组合（JSON）
     */
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    /**
     * 商品促销分解金额
     */
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    @TableField("real_amount")
    private BigDecimal realAmount;

    /**
     * 赠送积分
     */
    @TableField("gift_integration")
    private Integer giftIntegration;

    /**
     * 赠送成长值
     */
    @TableField("gift_growth")
    private Integer giftGrowth;

}
