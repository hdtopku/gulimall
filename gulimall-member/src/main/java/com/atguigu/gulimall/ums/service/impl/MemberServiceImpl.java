package com.atguigu.gulimall.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.atguigu.gulimall.ums.dao.MemberLevelDao;
import com.atguigu.gulimall.ums.entity.MemberLevelEntity;
import com.atguigu.gulimall.ums.exception.PhoneExistException;
import com.atguigu.gulimall.ums.exception.UsernameExistException;
import com.atguigu.gulimall.ums.vo.MemberLoginVo;
import com.atguigu.gulimall.vo.MemberRegisterVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ums.dao.MemberDao;
import com.atguigu.gulimall.ums.entity.MemberEntity;
import com.atguigu.gulimall.ums.service.MemberService;


@Service("memberService")
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    private final MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo vo) {
        MemberEntity entity = new MemberEntity();
//        检查用户名和手机号是否唯一
        Integer mobile = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().lambda().eq(MemberEntity::getMobile, vo.getPhone()));
        if (mobile > 0) {
            throw new PhoneExistException();
        }
        Integer userEntity = this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", vo.getUsername()));
        if (userEntity > 0) {
            throw new UsernameExistException();
        }
        entity.setUsername(vo.getUsername());
//        String passwd = Md5Crypt.md5Crypt(vo.getPassword().getBytes());
        entity.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        entity.setMobile(vo.getPhone());
        entity.setNickname(vo.getUsername());
//        设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.selectOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
        entity.setLevelId(levelEntity.getId());
        this.baseMapper.insert(entity);
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        MemberEntity entity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", vo.getLoginacct()).or().eq("mobile", vo.getLoginacct()));
        if (null != entity) {
            boolean match = new BCryptPasswordEncoder().matches(vo.getPassword(), entity.getPassword());
            if (match) {
                return entity;
            }
        }
        return null;
    }

}