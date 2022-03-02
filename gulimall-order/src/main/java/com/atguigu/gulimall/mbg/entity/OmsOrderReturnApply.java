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
 * 订单退货申请
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_order_return_apply")
public class OmsOrderReturnApply implements Serializable {

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
     * 退货商品id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 订单编号
     */
    @TableField("order_sn")
    private String orderSn;

    /**
     * 申请时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 会员用户名
     */
    @TableField("member_username")
    private String memberUsername;

    /**
     * 退款金额
     */
    @TableField("return_amount")
    private BigDecimal returnAmount;

    /**
     * 退货人姓名
     */
    @TableField("return_name")
    private String returnName;

    /**
     * 退货人电话
     */
    @TableField("return_phone")
    private String returnPhone;

    /**
     * 申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]
     */
    @TableField("status")
    private Boolean status;

    /**
     * 处理时间
     */
    @TableField("handle_time")
    private Date handleTime;

    /**
     * 商品图片
     */
    @TableField("sku_img")
    private String skuImg;

    /**
     * 商品名称
     */
    @TableField("sku_name")
    private String skuName;

    /**
     * 商品品牌
     */
    @TableField("sku_brand")
    private String skuBrand;

    /**
     * 商品销售属性(JSON)
     */
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    /**
     * 退货数量
     */
    @TableField("sku_count")
    private Integer skuCount;

    /**
     * 商品单价
     */
    @TableField("sku_price")
    private BigDecimal skuPrice;

    /**
     * 商品实际支付单价
     */
    @TableField("sku_real_price")
    private BigDecimal skuRealPrice;

    /**
     * 原因
     */
    @TableField("reason")
    private String reason;

    /**
     * 描述
     */
    @TableField("description述")
    private String description述;

    /**
     * 凭证图片，以逗号隔开
     */
    @TableField("desc_pics")
    private String descPics;

    /**
     * 处理备注
     */
    @TableField("handle_note")
    private String handleNote;

    /**
     * 处理人员
     */
    @TableField("handle_man")
    private String handleMan;

    /**
     * 收货人
     */
    @TableField("receive_man")
    private String receiveMan;

    /**
     * 收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;

    /**
     * 收货备注
     */
    @TableField("receive_note")
    private String receiveNote;

    /**
     * 收货电话
     */
    @TableField("receive_phone")
    private String receivePhone;

    /**
     * 公司收货地址
     */
    @TableField("company_address")
    private String companyAddress;

}
