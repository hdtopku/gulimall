package com.atguigu.gulimall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.pms.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:37
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

