package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

