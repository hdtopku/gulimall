package com.atguigu.gulimall.sms.dao;

import com.atguigu.gulimall.sms.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author ${author}
 * @email ${email}
 * @date 2022-03-14 10:39:25
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
