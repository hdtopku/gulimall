package com.atguigu.gulimall.thirdparty.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lxh
 * @createTime 2022-03-29 21:53:59
 */
@Component
@ConfigurationProperties(prefix = "alibaba.cloud.sms")
@Data
public class SmsComponent {
    private String host;
    private String path;
    private String skin;
    private String sign;
    private String appcode;
    public void sendSmsCode(String phone, String code) {

    }
}
