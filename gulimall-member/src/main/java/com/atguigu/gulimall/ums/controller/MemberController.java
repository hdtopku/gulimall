package com.atguigu.gulimall.ums.controller;

import java.util.Arrays;
import java.util.Map;

import com.atguigu.gulimall.common.utils.BizCodeEnum;
import com.atguigu.gulimall.ums.exception.PhoneExistException;
import com.atguigu.gulimall.ums.exception.UsernameExistException;
import com.atguigu.gulimall.ums.vo.MemberLoginVo;
import com.atguigu.gulimall.vo.MemberRegisterVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.ums.entity.MemberEntity;
import com.atguigu.gulimall.ums.service.MemberService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 会员
 *
 * @author ${author}
 * @email ${email}
 * @date 2022-03-12 19:53:44
 */
@RestController
@RequestMapping("ums/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("regist")
    public R register(@RequestBody MemberRegisterVo vo) {
        try {
            memberService.register(vo);
        } catch (UsernameExistException e) {
            return R.error(BizCodeEnum.USERNAME_EXIST_EXCEPTION.getCode(), BizCodeEnum.USERNAME_EXIST_EXCEPTION.getMsg());
        } catch (PhoneExistException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @PostMapping("login")
    public R register(@RequestBody MemberLoginVo vo) {
        MemberEntity entity = memberService.login(vo);
        if(null == entity) {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getCode(), BizCodeEnum.LOGINACCT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
        return R.ok().put("data", entity);
    }



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("ums:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ums:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("ums:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("ums:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("ums:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
