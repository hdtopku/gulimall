package com.atguigu.gulimall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.codec.ServerCodecConfigurer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimallGategayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallGategayApplication.class, args);
    }

}
