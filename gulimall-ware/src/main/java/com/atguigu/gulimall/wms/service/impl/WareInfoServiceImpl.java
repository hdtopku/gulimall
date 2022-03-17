package com.atguigu.gulimall.wms.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.wms.dao.WareInfoDao;
import com.atguigu.gulimall.wms.entity.WareInfoEntity;
import com.atguigu.gulimall.wms.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and(w-> w.eq("id", key).or().like("name", key)
                .or().like("address", key).or().like("areacode", key));
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),wrapper);
        return new PageUtils(page);
    }

}