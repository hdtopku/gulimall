package com.atguigu.gulimall.common.constant;

import lombok.Getter;

/**
 * @author lxh
 * @createTime 2022-03-15 21:19:52
 */
public class WareConstant {
    @Getter
    public enum PurchaseStatusEnum {
        CREATE(0, "新建"),
        ASSIGNED(1, "已分配"),
        RECEIVED(2, "已领取"),
        FINISHED(3, "已完成"),
        HASERROR(4, "有异常");
        private Integer code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
