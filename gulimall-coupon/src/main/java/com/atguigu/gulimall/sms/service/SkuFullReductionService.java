package com.atguigu.gulimall.sms.service;

import com.atguigu.gulimall.common.to.SkuReduceTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.sms.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-14 10:39:23
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduceTo(SkuReduceTo skuReduceTo);
}

