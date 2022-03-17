package com.atguigu.gulimall.wms.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.wms.dao.PurchaseDetailDao;
import com.atguigu.gulimall.wms.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.wms.service.PurchaseDetailService;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
        if (!StrUtil.isBlankIfStr(params.get("key"))) {
            wrapper.and(w->w.eq("purchase_id", params.get("key"))
                    .or().eq("sku_id", params.get("key")));
        }
        if (!StrUtil.isBlankIfStr(params.get("status"))) {
            wrapper.eq("status", params.get("status"));
        }
        if (!StrUtil.isBlankIfStr(params.get("wareId"))) {
            wrapper.eq("ware_id", params.get("wareId"));
        }
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),wrapper

        );

        return new PageUtils(page);
    }

}