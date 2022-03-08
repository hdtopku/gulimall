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
 * 会员收藏的专题活动
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member_collect_subject")
public class UmsMemberCollectSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * subject_id
     */
    @TableField("subject_id")
    private Long subjectId;

    /**
     * subject_name
     */
    @TableField("subject_name")
    private String subjectName;

    /**
     * subject_img
     */
    @TableField("subject_img")
    private String subjectImg;

    /**
     * 活动url
     */
    @TableField("subject_urll")
    private String subjectUrll;

}
