package com.atguigu.gulimall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ums.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-12 19:53:43
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

