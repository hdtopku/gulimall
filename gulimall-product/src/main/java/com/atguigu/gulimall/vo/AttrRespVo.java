package com.atguigu.gulimall.vo;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-11 00:11:15
 */
@Data
public class AttrRespVo extends AttrEntity {
    private String groupName;
    private String catelogName;

    private Long attrGroupId;
    private List<Long> catelogPath;
}
