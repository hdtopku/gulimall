package com.atguigu.gulimall.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author lxh
 * @createTime 2022-03-31 18:40:41
 */
@EnableRedisHttpSession
public class SpringSessionConfig {
//    cookie序列化器
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setDomainName("gulimall.com");
//       默认的cookieName为JSESSIONID，值为32位的字母和数字的组合，如：7DFC80549EB3AA3737B228915298BBCA
        defaultCookieSerializer.setCookieName("GULISESSION");
        return defaultCookieSerializer;
    }
//    redis序列化器
    @Bean
    public RedisSerializer<Object> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}