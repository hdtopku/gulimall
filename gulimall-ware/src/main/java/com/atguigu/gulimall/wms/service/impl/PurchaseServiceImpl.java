package com.atguigu.gulimall.wms.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.atguigu.gulimall.common.constant.WareConstant;
import com.atguigu.gulimall.wms.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.wms.service.PurchaseDetailService;
import com.atguigu.gulimall.wms.service.WareSkuService;
import com.atguigu.gulimall.wms.vo.MergeVo;
import com.atguigu.gulimall.wms.vo.PurchaseDoneVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.wms.dao.PurchaseDao;
import com.atguigu.gulimall.wms.entity.PurchaseEntity;
import com.atguigu.gulimall.wms.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseService")
@RequiredArgsConstructor
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {
    private final PurchaseDetailService purchaseDetailService;
    private final WareSkuService wareSkuService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>().eq("status", 0).or().eq("status", 1));
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.CREATE.getCode());
            this.save(purchaseEntity);
            purchaseId = purchaseEntity.getId();
        }
        List<Long> items = mergeVo.getItems();
        if (CollUtil.isNotEmpty(items)) {
            QueryWrapper<PurchaseDetailEntity> wrapper = new QueryWrapper<>();
            wrapper.in("id", items).and(w -> w.eq("status", WareConstant.PurchaseStatusEnum.CREATE.getCode())
                    .or().eq("status", WareConstant.PurchaseStatusEnum.ASSIGNED.getCode()));
            items = purchaseDetailService.list(wrapper).stream().map(PurchaseDetailEntity::getId).collect(Collectors.toList());
        }
        if (CollUtil.isNotEmpty(items)) {
            Long finalPurchaseId = purchaseId;
            purchaseDetailService.list(new QueryWrapper<>());
            List<PurchaseDetailEntity> collect = items.stream().map(i -> {
                PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
                detailEntity.setId(i);
                detailEntity.setPurchaseId(finalPurchaseId);
                detailEntity.setStatus(WareConstant.PurchaseStatusEnum.ASSIGNED.getCode());
                return detailEntity;
            }).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(collect);
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setId(finalPurchaseId);
            purchaseEntity.setUpdateTime(new Date());
            this.updateById(purchaseEntity);
        }
    }

    /**
     * 采购单的id
     * @param ids
     */
    @Override
    public void received(List<Long> ids) {
//        1、确认当前采购单是新建或者已分配状态
        List<PurchaseEntity> entities = ids.stream().map(this::getById).filter(item ->
                item.getStatus().equals(WareConstant.PurchaseStatusEnum.CREATE.getCode())
                || item.getStatus().equals(WareConstant.PurchaseStatusEnum.ASSIGNED.getCode())).map(item -> {
            item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVED.getCode());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());
//        2、改变采购单的状态
        this.updateBatchById(entities);
//        3、改变采购单的状态
        entities.forEach(item->{
             List<PurchaseDetailEntity> entityList = purchaseDetailService.list(
                    new QueryWrapper<PurchaseDetailEntity>().eq("purchase_id", item.getId()));
             List<PurchaseDetailEntity> detailEntities = entityList.stream().map(entity->{
                 PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
                 detailEntity.setId(entity.getId());
                 detailEntity.setStatus(WareConstant.PurchaseStatusEnum.RECEIVED.getCode());
                 return detailEntity;
             }).collect(Collectors.toList());
             purchaseDetailService.updateBatchById(detailEntities);
        });
    }

    @Override
    @Transactional
    public void done(PurchaseDoneVo doneVo) {
//        1、改变采购单的状态
        purchaseDetailService.getById(doneVo.getId());
        AtomicReference<Boolean> success = new AtomicReference<>(true);

        List<PurchaseDetailEntity> collect = doneVo.getItems().stream().map(item -> {
            if (item.getStatus().equals(WareConstant.PurchaseStatusEnum.HASERROR.getCode())) {
                success.set(false);
            } else {
//        3、将成功采购单入库
                PurchaseDetailEntity entity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(entity.getSkuId(), entity.getWareId(), entity.getSkuNum());
            }
            PurchaseDetailEntity entity = new PurchaseDetailEntity();
            entity.setStatus(item.getStatus());
            entity.setId(item.getItemId());
            return entity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(collect);
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        if (success.get()) {
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.FINISHED.getCode());
        } else {
            purchaseEntity.setStatus(WareConstant.PurchaseStatusEnum.HASERROR.getCode());
        }
//        2、改变采购单状态
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setId(doneVo.getId());
        this.updateById(purchaseEntity);


    }

}