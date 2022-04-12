package com.atguigu.gulimall.sms.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.sms.dao.SeckillSessionDao;
import com.atguigu.gulimall.sms.entity.SeckillSessionEntity;
import com.atguigu.gulimall.sms.entity.SeckillSkuRelationEntity;
import com.atguigu.gulimall.sms.service.SeckillSessionService;
import com.atguigu.gulimall.sms.service.SeckillSkuRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("seckillSessionService")
@RequiredArgsConstructor
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {
    private final SeckillSkuRelationService seckillSkuRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {
        QueryWrapper<SeckillSessionEntity> wrapper = new QueryWrapper<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime now = LocalDateTimeUtil.now();
        String startTime = LocalDateTimeUtil.beginOfDay(now).format(formatter);
        String endTime = LocalDateTimeUtil.endOfDay(now.plusDays(2)).format(formatter);
        wrapper.between("start_time", startTime, endTime);
        List<SeckillSessionEntity> list = this.list(wrapper);
        return Optional.ofNullable(list).orElse(Collections.emptyList()).stream().peek(
                item-> item.setRelactionSkus(seckillSkuRelationService.list(
                        new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id", item.getId()))))
                .collect(Collectors.toList());
    }
}