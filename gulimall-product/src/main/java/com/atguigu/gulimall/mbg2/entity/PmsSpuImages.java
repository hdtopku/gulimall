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
 * spu图片
 * </p>
 *
 * @author 
 * @since 2022-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_spu_images")
public class PmsSpuImages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu_id
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 图片名
     */
    @TableField("img_name")
    private String imgName;

    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;

    /**
     * 顺序
     */
    @TableField("img_sort")
    private Integer imgSort;

    /**
     * 是否默认图
     */
    @TableField("default_img")
    private Integer defaultImg;

}
