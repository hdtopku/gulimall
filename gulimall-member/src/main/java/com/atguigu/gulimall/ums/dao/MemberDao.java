package com.atguigu.gulimall.ums.dao;

import com.atguigu.gulimall.ums.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-03-12 19:53:44
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
