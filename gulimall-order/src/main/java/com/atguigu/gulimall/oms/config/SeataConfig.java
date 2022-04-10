//package com.atguigu.gulimall.oms.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
///**
// * @author lxh
// * @createTime 2022-04-07 17:16:38
// */
//@Configuration
//public class SeataConfig {
//    @Resource
//    private DataSourceProperties dataSourceProperties;
//    @Bean
//    public DataSource dataSource() {
//        HikariDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        if (StringUtils.hasText(dataSourceProperties.getName())) {
//            dataSource.setPoolName(dataSourceProperties.getName());
//        }
//        return new DataSourceProxy(dataSource);
//    }
//}
