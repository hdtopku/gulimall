package com.atguigu.gulimall.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author lxh
 * @createTime 2022-03-22 15:10:20
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.datasource.password}")
    private String password;
    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @return
     * @throws IOException
     */
    @Bean(destroyMethod="shutdown")
    RedissonClient redissonClient() throws IOException {
        Config config = new Config();
//        集群
//        config.useClusterServers()
        //可以用"rediss://"来启用SSL连接
//        .addNodeAddress("redis://127.0.0.1:7004", "127.0.0.1:7001");
//        单节点
        config.useSingleServer().setAddress("redis://121.41.170.120:6379").setPassword(password);
        return Redisson.create(config);
    }
}
