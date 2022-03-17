package com.atguigu.gulimall.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.atguigu.gulimall.common.to.MemberPrice;
import com.atguigu.gulimall.common.to.SkuReduceTo;
import com.atguigu.gulimall.sms.entity.MemberPriceEntity;
import com.atguigu.gulimall.sms.entity.SkuLadderEntity;
import com.atguigu.gulimall.sms.service.MemberPriceService;
import com.atguigu.gulimall.sms.service.SkuLadderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.sms.dao.SkuFullReductionDao;
import com.atguigu.gulimall.sms.entity.SkuFullReductionEntity;
import com.atguigu.gulimall.sms.service.SkuFullReductionService;


@Service("skuFullReductionService")
@RequiredArgsConstructor
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {
    private final SkuLadderService ladderService;
    private final MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduceTo(SkuReduceTo skuReduceTo) {
//        5.4sku的优惠满减信息：gulimall_sms->sms_sku_ladder
        if (skuReduceTo.getDiscount().doubleValue() <= 0 && skuReduceTo.getFullCount() <= 0
                && skuReduceTo.getReducePrice().doubleValue() <= 0) {
            return;
        }
        SkuLadderEntity ladderEntity = BeanUtil.copyProperties(skuReduceTo, SkuLadderEntity.class);
        ladderEntity.setAddOther(skuReduceTo.getCountStatus());
        ladderService.save(ladderEntity);
//        sms_sku_full_reduction
        SkuFullReductionEntity reductionEntity = BeanUtil.copyProperties(skuReduceTo, SkuFullReductionEntity.class);
        this.save(reductionEntity);
//        sms_member_price
        List<MemberPrice> memberPrice = skuReduceTo.getMemberPrice();
        if (CollUtil.isNotEmpty(memberPrice)) {
            List<MemberPriceEntity> priceEntities = memberPrice.stream().filter(item -> item.getPrice().doubleValue() > 0)
                    .map(item -> {
                        MemberPriceEntity priceEntity = new MemberPriceEntity();
                        priceEntity.setSkuId(item.getId());
                        priceEntity.setMemberLevelId(item.getId());
                        priceEntity.setMemberLevelName(item.getName());
                        priceEntity.setMemberPrice(item.getPrice());
                        priceEntity.setAddOther(1);
                        return priceEntity;
                    }).collect(Collectors.toList());
            memberPriceService.saveBatch(priceEntities);
        }
    }

}