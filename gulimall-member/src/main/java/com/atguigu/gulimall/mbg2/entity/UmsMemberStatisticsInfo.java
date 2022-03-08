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
 * 会员统计信息
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_statistics_info")
public class UmsMemberStatisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员id
     */
    @TableField("member_id")
    private Long memberId;

    /**
     * 累计消费金额
     */
    @TableField("consume_amount")
    private BigDecimal consumeAmount;

    /**
     * 累计优惠金额
     */
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    /**
     * 订单数量
     */
    @TableField("order_count")
    private Integer orderCount;

    /**
     * 优惠券数量
     */
    @TableField("coupon_count")
    private Integer couponCount;

    /**
     * 评价数
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 退货数量
     */
    @TableField("return_order_count")
    private Integer returnOrderCount;

    /**
     * 登录次数
     */
    @TableField("login_count")
    private Integer loginCount;

    /**
     * 关注数量
     */
    @TableField("attend_count")
    private Integer attendCount;

    /**
     * 粉丝数量
     */
    @TableField("fans_count")
    private Integer fansCount;

    /**
     * 收藏的商品数量
     */
    @TableField("collect_product_count")
    private Integer collectProductCount;

    /**
     * 收藏的专题活动数量
     */
    @TableField("collect_subject_count")
    private Integer collectSubjectCount;

    /**
     * 收藏的评论数量
     */
    @TableField("collect_comment_count")
    private Integer collectCommentCount;

    /**
     * 邀请的朋友数量
     */
    @TableField("invite_friend_count")
    private Integer inviteFriendCount;

}
