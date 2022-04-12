package com.atguigu.gulimall.common.config.must;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxh
 * @createTime 2022-03-02 01:40:17
 */
//启用服务发现
@EnableDiscoveryClient
//启用服务调用
@Configuration
public class NacosConfig {
}
