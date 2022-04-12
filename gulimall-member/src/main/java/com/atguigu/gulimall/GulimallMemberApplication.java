package com.atguigu.gulimall;

import com.atguigu.gulimall.common.config.must.NacosConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

@SpringBootApplication
@ConditionalOnClass(NacosConfig.class)
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
