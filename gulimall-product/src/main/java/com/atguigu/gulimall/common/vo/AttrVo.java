package com.atguigu.gulimall.common.vo;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import lombok.Data;

/**
 * @author lxh
 * @date 2022-03-10 22:58:22
 */
@Data
public class AttrVo extends AttrEntity {
    /**
     * 分组id
     */
    private Long attrGroupId;
}
