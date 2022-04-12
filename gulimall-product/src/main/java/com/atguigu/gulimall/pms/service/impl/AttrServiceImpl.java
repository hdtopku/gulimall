package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.common.constant.ProductConstant;
import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.dao.AttrDao;
import com.atguigu.gulimall.pms.dao.AttrGroupDao;
import com.atguigu.gulimall.pms.dao.CategoryDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.pms.service.AttrService;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.common.vo.AttrRespVo;
import com.atguigu.gulimall.common.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
@RequiredArgsConstructor
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    private final AttrAttrgroupRelationDao relationDao;
    private final AttrAttrgroupRelationService attrAttrgroupRelationService;
    private final AttrGroupDao attrGroupDao;
    private final CategoryDao categoryDao;
    private final CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        // 保存基本数据
        this.save(attrVo);
//        保存关联关系
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && null != attrVo.getAttrGroupId()) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationEntity.setAttrId(attrVo.getAttrId());
            relationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_type", "base".equalsIgnoreCase(attrType) ?
                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) {
            wrapper.eq("catelog_id", catelogId);
        }
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key)
                        .or().like("value_select", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params), wrapper
        );
        List<AttrRespVo> attrRespVoList = page.getRecords().stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = BeanUtil.copyProperties(attrEntity, AttrRespVo.class);
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            if (attrAttrgroupRelationEntity != null) {
                AttrGroupEntity groupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
                if (null != groupEntity) {
                    attrRespVo.setGroupName(groupEntity.getAttrGroupName());
                }
                CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
                if (null != categoryEntity) {
                    attrRespVo.setCatelogName(categoryEntity.getName());
                }
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrRespVoList);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = BeanUtil.copyProperties(attrEntity, AttrRespVo.class);
//        设置分组信息
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
        if (attrAttrgroupRelationEntity != null && null != attrAttrgroupRelationEntity.getAttrGroupId()) {
            AttrGroupEntity groupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
            if (null != groupEntity) {
                attrRespVo.setGroupName(groupEntity.getAttrGroupName());
                attrRespVo.setAttrGroupId(groupEntity.getAttrGroupId());
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getAttrId());
            if (null != categoryEntity) {
                attrRespVo.setCatelogName(categoryEntity.getName());
                attrRespVo.setCatelogPath(categoryService.findCategoryPath(attrEntity.getCatelogId()));
            }
        }
        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVo attrVo) {
        this.updateById(attrVo);
        AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
        entity.setAttrGroupId(attrVo.getAttrGroupId());
        entity.setAttrId(attrVo.getAttrId());
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrVo.getAttrId());
        attrAttrgroupRelationService.saveOrUpdate(entity, wrapper);
    }

    @Override
    public List<AttrEntity> getAttrRelation(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        if (CollUtil.isNotEmpty(relationEntities)) {
            List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(attrIds)) {
                return this.listByIds(attrIds);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 获取当前分组，没有关联的所有属性
     *
     * @param attrgroupId
     * @param params
     * @return
     */
    @Override
    public PageUtils getAttrNoRelation(Long attrgroupId, Map<String, Object> params) {
//       查找出当前分类下，未被所有分组关联的attrEntity
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", attrGroupEntity.getCatelogId());
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", attrGroupEntity.getCatelogId()));
        if (CollUtil.isNotEmpty(attrGroupEntities)) {
            List<Long> attrGroupIds = attrGroupEntities.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());
            List<AttrAttrgroupRelationEntity> relationEntities = attrAttrgroupRelationService.list(
                    new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIds));
            if (CollUtil.isNotEmpty(relationEntities)) {
                List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(attrIds)) {
                    wrapper.notIn("attr_id", attrIds);
                }
            }
        }
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank(key)) {
            wrapper.and(w->{
                w.eq("attr_id", key).or().like("attr_name", key).or().like("value_select", key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }
}