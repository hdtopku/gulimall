package com.atguigu.gulimall.mbg2.entity;
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
 * spu信息
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_info")
public class PmsSpuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    @TableField("spu_name")
    private String spuName;

    /**
     * 商品描述
     */
    @TableField("spu_description")
    private String spuDescription;

    /**
     * 所属分类id
     */
    @TableField("catalog_id")
    private Long catalogId;

    /**
     * 品牌id
     */
    @TableField("brand_id")
    private Long brandId;

    @TableField("weight")
    private BigDecimal weight;

    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    @TableField("publish_status")
    private Integer publishStatus;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

}
