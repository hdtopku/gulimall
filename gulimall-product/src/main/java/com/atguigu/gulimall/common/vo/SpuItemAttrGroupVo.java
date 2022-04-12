package com.atguigu.gulimall.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lxh
 * @createTime 2022-03-28 21:46:55
 */
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<SpuBaseAttrVo> attrs;
}