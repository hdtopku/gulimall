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
 * sku销售属性&值
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_sku_sale_attr_value")
public class PmsSkuSaleAttrValue implements Serializable {

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
     * attr_id
     */
    @TableField("attr_id")
    private Long attrId;

    /**
     * 销售属性名
     */
    @TableField("attr_name")
    private String attrName;

    /**
     * 销售属性值
     */
    @TableField("attr_value")
    private String attrValue;

    /**
     * 顺序
     */
    @TableField("attr_sort")
    private Integer attrSort;

}
