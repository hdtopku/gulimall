package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.dao.AttrGroupDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.pms.service.AttrGroupService;
import com.atguigu.gulimall.pms.service.AttrService;
import com.atguigu.gulimall.common.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.common.vo.AttrGroupWithAttrsVo;
import com.atguigu.gulimall.common.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
@RequiredArgsConstructor
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    private final AttrAttrgroupRelationDao relationDao;
    private final AttrAttrgroupRelationService relationService;
    private final AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (catelogId > 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        if (StrUtil.isNotBlank((String)params.get("key"))) {
            wrapper.and((w)-> w.eq("attr_group_id", params.get("key")).or().like("attr_group_name", params.get("key"))
                    .or().like("descript", params.get("key")));
        }
        return new PageUtils(this.page(
                new Query<AttrGroupEntity>().getPage(params), wrapper));
    }


    @Override
    public int deleteRelation(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        List<AttrAttrgroupRelationEntity> entities = attrGroupRelationVoList.stream()
                .map(item-> BeanUtil.copyProperties(item, AttrAttrgroupRelationEntity.class)).collect(Collectors.toList());

        return relationDao.deleteBatchRelation(entities);
    }

    @Override
    public void addRelation(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrGroupRelationVoList.stream().map(
                item -> BeanUtil.copyProperties(item, AttrAttrgroupRelationEntity.class)).collect(Collectors.toList());
        relationService.saveOrUpdateBatch(relationEntities);
    }

    /**
     * 根据分类id查询所有分组以及这些分组里面的属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId) {
        List<AttrGroupEntity> entities =this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        if (CollUtil.isNotEmpty(entities)) {
            return entities.stream().map(item -> {
                AttrGroupWithAttrsVo attrsVo = BeanUtil.copyProperties(item, AttrGroupWithAttrsVo.class);
                attrsVo.setAttrs(attrService.getAttrRelation(item.getAttrGroupId()));
                return attrsVo;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {
//        查出当前spu对应的所有属性的分组信息以及当前分组下的所有属性对应的值
//        当前sku的spu对应的属性分组
         return baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
    }
}