package com.atguigu.gulimall.sms.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.sms.entity.HomeSubjectEntity;
import com.atguigu.gulimall.sms.service.HomeSubjectService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;


/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author $ {author}
 * @email $ {email}
 * @date 2022 -03-14 10:39:24
 */
@RestController
@RequestMapping("sms/homesubject")
public class HomeSubjectController {
    @Autowired
    private HomeSubjectService homeSubjectService;

    /**
     * 列表
     *
     * @param params the params
     * @return the r
     */
    @RequestMapping("/list")
    @RequiresPermissions("sms:homesubject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = homeSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     *
     * @param id the id
     * @return the r
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sms:homesubject:info")
    public R info(@PathVariable("id") Long id){
		HomeSubjectEntity homeSubject = homeSubjectService.getById(id);

        return R.ok().put("homeSubject", homeSubject);
    }

    /**
     * 保存
     *
     * @param homeSubject the home subject
     * @return the r
     */
    @RequestMapping("/save")
    @RequiresPermissions("sms:homesubject:save")
    public R save(@RequestBody HomeSubjectEntity homeSubject){
		homeSubjectService.save(homeSubject);

        return R.ok();
    }

    /**
     * 修改
     *
     * @param homeSubject the home subject
     * @return the r
     */
    @RequestMapping("/update")
    @RequiresPermissions("sms:homesubject:update")
    public R update(@RequestBody HomeSubjectEntity homeSubject){
		homeSubjectService.updateById(homeSubject);

        return R.ok();
    }

    /**
     * 删除
     *
     * @param ids the ids
     * @return the r
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sms:homesubject:delete")
    public R delete(@RequestBody Long[] ids){
		homeSubjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
