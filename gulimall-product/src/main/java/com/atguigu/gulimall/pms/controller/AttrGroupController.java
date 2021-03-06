package com.atguigu.gulimall.pms.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.hutool.core.util.ObjectUtil;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.service.AttrService;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.common.vo.AttrGroupRelationVo;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrGroupService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 属性分组
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
@RestController
@RequestMapping("pms/attrgroup")
@RequiredArgsConstructor
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    private final AttrService attrService;


    @GetMapping("{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId, @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getAttrNoRelation(attrgroupId, params);
        return R.ok().put("data", page);
    }

    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> attrEntities = attrService.getAttrRelation(attrgroupId);
        return R.ok().put("data", attrEntities);
    }

    @PostMapping("attr/relation/delete")
    public R deleteRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrGroupService.deleteRelation(attrGroupRelationVoList);
        return R.ok();
    }

    @PostMapping("attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrGroupService.addRelation(attrGroupRelationVoList);
        return R.ok();
    }

    @GetMapping("{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        return R.ok().put("data", attrGroupService.getAttrGroupWithAttrs(catelogId));
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    @RequiresPermissions("pms:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    @RequiresPermissions("pms:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
		if (ObjectUtil.isNotNull(attrGroup)) {
            List<Long> categoryPath = categoryService.findCategoryPath(attrGroup.getCatelogId());
            attrGroup.setCatelogPath(categoryPath);
        }
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("pms:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("pms:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("pms:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
