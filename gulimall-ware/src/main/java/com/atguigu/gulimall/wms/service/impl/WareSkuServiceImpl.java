package com.atguigu.gulimall.wms.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.wms.dao.WareSkuDao;
import com.atguigu.gulimall.wms.entity.WareSkuEntity;
import com.atguigu.gulimall.wms.feign.PmsFeignService;
import com.atguigu.gulimall.wms.service.WareSkuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareSkuService")
@RequiredArgsConstructor
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {
    private final WareSkuDao wareSkuDao;
    private final PmsFeignService pmsFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        if (StrUtil.isNotBlank(skuId)) {
            wrapper.eq("sku_id", skuId);
        }
        String wareId = (String) params.get("wareId");
        if (StrUtil.isNotBlank(wareId)) {
            wrapper.eq("ware_id", wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),wrapper);

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //    判断如果还没有这个库存记录，则新增
        WareSkuEntity skuEntity = wareSkuDao.selectOne(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (ObjectUtil.isNull(skuEntity)) {
            WareSkuEntity skuEntity1 = new WareSkuEntity();
            skuEntity1.setSkuId(skuId);
            skuEntity1.setWareId(wareId);
            skuEntity1.setStock(skuNum);
            skuEntity1.setStockLocked(0);
//          远程查询sku的名字，如果失败，事务不回滚
//          TODO 除了try catch，还可以有什么办法，让异常事务不回滚
            try {
                R info = pmsFeignService.info(skuId);

                if (info.getCode() == 0) {
                    Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                    skuEntity1.setSkuName((String) data.get("skuName"));
                }
            } catch (Exception e) {

            }
            skuEntity1.setSkuName("");
            wareSkuDao.insert(skuEntity1);
        } else {
            wareSkuDao.addStock(skuId, wareId, skuNum);
        }
    }

}