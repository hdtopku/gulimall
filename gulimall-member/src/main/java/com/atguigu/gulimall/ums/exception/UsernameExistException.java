package com.atguigu.gulimall.ums.exception;

/**
 * @author lxh
 * @createTime 2022-03-30 16:20:46
 */
public class UsernameExistException extends RuntimeException {
    public UsernameExistException() {
        super("用户名已存在");
    }
}
