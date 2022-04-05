package com.atguigu.gulimall.ums.exception;

import lombok.NoArgsConstructor;

/**
 * @author lxh
 * @createTime 2022-03-30 16:21:12
 */
public class PhoneExistException extends RuntimeException{
    public PhoneExistException() {
        super("手机已被注册");
    }
}
