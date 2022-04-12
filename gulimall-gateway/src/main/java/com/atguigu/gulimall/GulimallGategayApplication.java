package com.atguigu.gulimall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * The type Gulimall gategay application.
 * @author lxh
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(excludeFilters  = {@ComponentScan.Filter(type = FilterType.REGEX,
        pattern = {"com.atguigu.gulimall.common.config.optional.*" })})
public class GulimallGategayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallGategayApplication.class, args);
    }

}
