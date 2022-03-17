package com.atguigu.gulimall.pms.dao;

import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    int deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
