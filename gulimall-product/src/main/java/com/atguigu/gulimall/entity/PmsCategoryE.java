package com.atguigu.gulimall.entity;

import com.atguigu.gulimall.mbg.entity.PmsCategory;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @Description TODO
 * @createTime 2022-03-05 13:29:35
 */
@Data
public class PmsCategoryE extends PmsCategory {
    private List<PmsCategory> children;
}
