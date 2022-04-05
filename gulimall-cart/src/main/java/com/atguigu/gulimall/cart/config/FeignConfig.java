//package com.atguigu.gulimall.cart.config;
//
//import feign.RequestInterceptor;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author lxh
// * @createTime 2022-04-05 19:22:37
// */
//@Configuration
//public class FeignConfig {
//    @Bean("requestInterceptor")
//    public RequestInterceptor requestInterceptor() {
//        return template -> {
//            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//            if (null == requestAttributes) {
//                return;
//            }
//            HttpServletRequest request = requestAttributes.getRequest();
//            // 拿到原始请求头数据
//            String cookie = request.getHeader("Cookie");
//            if (StringUtils.isNotBlank(cookie)) {
//                // 同步
//                template.header("Cookie", cookie);
//            }
//        };
//    }
//}
