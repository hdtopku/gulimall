package com.atguigu.gulimall.vo;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-13 10:15:33
 */
@Data
public class AttrGroupWithAttrsVo extends AttrGroupEntity {
    private List<AttrEntity> attrs;
}
