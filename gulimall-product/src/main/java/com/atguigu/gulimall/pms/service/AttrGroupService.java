package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.vo.AttrGroupWithAttrsVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    int deleteRelation(List<AttrGroupRelationVo> attrGroupRelationVoList);

    void addRelation(List<AttrGroupRelationVo> attrGroupRelationVoList);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId);
}

