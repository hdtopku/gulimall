package com.atguigu.gulimall.pms.controller;

import cn.hutool.core.util.StrUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.common.validate.group.AddGroup;
import com.atguigu.gulimall.common.validate.group.UpdateGroup;
import com.atguigu.gulimall.common.validate.group.UpdateStatusGroup;
import com.atguigu.gulimall.pms.entity.BrandEntity;
import com.atguigu.gulimall.pms.service.BrandService;
import com.atguigu.gulimall.pms.service.CategoryBrandRelationService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-07 14:52:38
 */
@RestController
@RequestMapping("pms/brand")
@RequiredArgsConstructor
public class BrandController {
    @Autowired
    private BrandService brandService;
    private final CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("pms:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    @RequiresPermissions("pms:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("pms:brand:save")
    public R save(@RequestBody @Validated(AddGroup.class) BrandEntity brand /*, BindingResult bindingResult*/){
//        if (bindingResult.hasErrors()) {
////            HashMap<Object, Object> errorMap = new HashMap<>();
//            return R.error("提交数据不合法").put("data", bindingResult.getFieldErrors().stream()
//                    .map(error->
//                            StrUtil.builder().append(error.getDefaultMessage()).append(error.getRejectedValue()) )
//                    .collect(Collectors.toList()));
//        }
		brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("pms:brand:update")
    public R update(@RequestBody @Validated(UpdateGroup.class) BrandEntity brand){
		brandService.updateDetail(brand);
        if (StrUtil.isNotBlank(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
        return R.ok();
    }

    /**
     * 修改显示状态
     */
    @RequestMapping("/update/status")
    @RequiresPermissions("pms:brand:update")
    public R updateStatus(@RequestBody @Validated(UpdateStatusGroup.class) BrandEntity brand){
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("pms:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
