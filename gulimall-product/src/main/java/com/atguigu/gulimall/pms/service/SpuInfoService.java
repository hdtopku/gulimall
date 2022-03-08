package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.SpuInfoEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:37
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

