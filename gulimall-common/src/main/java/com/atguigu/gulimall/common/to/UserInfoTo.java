package com.atguigu.gulimall.common.to;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lxh
 * @createTime 2022-03-31 20:18:18
 */
@Data
public class UserInfoTo implements Serializable {
    private Long UserId;
    private String userKey;
    private boolean tempUser = false; //是否为临时用户
}
