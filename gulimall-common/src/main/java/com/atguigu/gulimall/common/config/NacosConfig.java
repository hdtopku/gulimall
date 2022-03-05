package com.atguigu.gulimall.common.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 * @Description TODO
 * @createTime 2022-03-02 01:40:17
 */
//启用服务发现
@EnableDiscoveryClient
//启用服务调用
@EnableFeignClients(basePackages = {"com.atguigu.gulimall"})
@Configuration
public class NacosConfig {
}
