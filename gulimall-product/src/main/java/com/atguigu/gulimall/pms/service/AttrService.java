package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

