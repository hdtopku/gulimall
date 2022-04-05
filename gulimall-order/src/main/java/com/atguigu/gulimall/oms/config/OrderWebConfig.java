package com.atguigu.gulimall.oms.config;

import com.atguigu.gulimall.oms.interceptor.LoginUserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lxh
 * @createTime 2022-04-05 00:39:03
 */
@Configuration
@RequiredArgsConstructor
public class OrderWebConfig implements WebMvcConfigurer {
    private final LoginUserInterceptor loginUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**");
    }
}
