package com.atguigu.gulimall.entity;

import com.atguigu.gulimall.mbg2.entity.PmsCategory;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-05 13:29:35
 */
@Data
public class PmsCategoryE extends PmsCategory {
    /**
     * 是否显示[0-不显示，1显示]
     */
    @TableField("show_status")
    @TableLogic(value = "1", delval = "0")
    private Integer showStatus;

    private List<PmsCategory> children;
}
