package com.atguigu.gulimall.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lxh
 * @createTime 2022-03-08 17:15:21
 */
@AllArgsConstructor
@Getter
public enum  BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败");
    private int code;
    private String msg;
}
