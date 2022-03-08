package com.atguigu.gulimall.mbg2.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_comment")
public class PmsSpuComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * sku_id
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * spu_id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 商品名字
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * 会员昵称
     */
    @TableField("member_nick_name")
    private String memberNickName;

    /**
     * 星级
     */
    @TableField("star")
    private Boolean star;

    /**
     * 会员ip
     */
    @TableField("member_ip")
    private String memberIp;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 显示状态[0-不显示，1-显示]
     */
    @TableField("show_status")
    @TableLogic
    private Boolean showStatus;

    /**
     * 购买时属性组合
     */
    @TableField("spu_attributes")
    private String spuAttributes;

    /**
     * 点赞数
     */
    @TableField("likes_count")
    private Integer likesCount;

    /**
     * 回复数
     */
    @TableField("reply_count")
    private Integer replyCount;

    /**
     * 评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]
     */
    @TableField("resources")
    private String resources;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 用户头像
     */
    @TableField("member_icon")
    private String memberIcon;

    /**
     * 评论类型[0 - 对商品的直接评论，1 - 对评论的回复]
     */
    @TableField("comment_type")
    private Integer commentType;

}
