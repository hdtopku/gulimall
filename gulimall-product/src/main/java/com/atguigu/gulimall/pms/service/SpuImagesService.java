package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.SpuImagesEntity;

import java.util.Map;

/**
 * spu图片
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:37
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

