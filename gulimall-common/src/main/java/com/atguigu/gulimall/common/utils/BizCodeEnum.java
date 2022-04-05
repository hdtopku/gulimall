package com.atguigu.gulimall.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lxh
 * @createTime 2022-03-08 17:15:21
 * 10: 通用
 *     10001:参数格式校验
 *     10002:短信验证码频率太高
 * 11: 商品
 *     11000:商品上架异常
 * 15: 用户
 *     15001:用户已存在
 *     15002:手机已存在
 *     15003:账号或密码错误
 */
@AllArgsConstructor
@Getter
public enum  BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    VALID_CODE_EXCEPTION(10002, "验证码获取频率太高，请稍后重试"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    USERNAME_EXIST_EXCEPTION(15001, "用户已存在"),
    PHONE_EXIST_EXCEPTION(15002, "手机已存在"),
    LOGINACCT_PASSWORD_INVALID_EXCEPTION(15003, "账号或密码错误");
    private int code;
    private String msg;
}
