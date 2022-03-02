package com.atguigu.gulimall;

import cn.hutool.core.util.StrUtil;
import com.github.davidfantasy.mybatisplus.generatorui.GeneratorConfig;
import com.github.davidfantasy.mybatisplus.generatorui.MybatisPlusToolsApplication;
import com.github.davidfantasy.mybatisplus.generatorui.mbp.NameConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author lxh
 * @Description TODO
 * @createTime 2021-01-20 23:08:02
 */
@SpringBootApplication
public class Generator {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Generator.class, args);
        Environment environment = context.getBean(Environment.class);
        String password = environment.getProperty("spring.datasource.password");

        GeneratorConfig config = GeneratorConfig.builder().jdbcUrl("jdbc:mysql://121.41.170.120/gulimall_oms?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai")
                .userName("root")
                .password(password)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                //数据库schema，POSTGRE_SQL,ORACLE,DB2类型的数据库需要指定
                .schemaName("myBusiness")
                //如果需要修改各类生成文件的默认命名规则，可自定义一个NameConverter实例，覆盖相应的名称转换方法：
                .nameConverter(new NameConverter() {
                    @Override
                    public String entityNameConvert(String tableName) {
                        return StrUtil.upperFirst(this.propertyNameConvert(tableName));
                    }

                    @Override
                    public String serviceImplNameConvert(String tableName) {
                        return "I" + this.entityNameConvert(tableName) + "ServiceImpl";
                    }

                })
                .basePackage("com.atguigu.gulimall.mbg")
                .port(8068)
                .build();
        MybatisPlusToolsApplication.run(config);
    }

}