package com.atguigu.gulimall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ums.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-12 19:53:44
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

