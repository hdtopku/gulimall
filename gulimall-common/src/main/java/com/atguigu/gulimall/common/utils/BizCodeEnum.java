package com.atguigu.gulimall.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enum Biz code enum.
 *
 * @author lxh
 * @createTime 2022 -03-08 17:15:21 10: 通用     10001:参数格式校验     10002:短信验证码频率太高 11: 商品     11000:商品上架异常 15: 用户     15001:用户已存在     15002:手机已存在     15003:账号或密码错误
 */
@AllArgsConstructor
@Getter
public enum  BizCodeEnum {
    /**
     * Unknown exception biz code enum.
     */
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    /**
     * Valid exception biz code enum.
     */
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    /**
     * Valid code exception biz code enum.
     */
    VALID_CODE_EXCEPTION(10002, "验证码获取频率太高，请稍后重试"),
    /**
     * To many request biz code enum.
     */
    TO_MANY_REQUEST(10002, "请求流量过大"),
    /**
     * Product up exception biz code enum.
     */
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    /**
     * Username exist exception biz code enum.
     */
    USERNAME_EXIST_EXCEPTION(15001, "用户已存在"),
    /**
     * Phone exist exception biz code enum.
     */
    PHONE_EXIST_EXCEPTION(15002, "手机已存在"),
    /**
     * Loginacct password invalid exception biz code enum.
     */
    LOGINACCT_PASSWORD_INVALID_EXCEPTION(15003, "账号或密码错误");
    private int code;
    private String msg;
}
