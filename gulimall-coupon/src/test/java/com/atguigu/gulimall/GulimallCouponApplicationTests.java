package com.atguigu.gulimall;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@SpringBootTest
public class GulimallCouponApplicationTests {

    @Test
    void contextLoads() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus3 = now.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        LocalDateTime now1 = LocalDateTimeUtil.now();
        System.out.println(now.format(formatter));
        System.out.println(plus3.format(formatter));
        System.out.println(now1.format(formatter));
        System.out.println("----------");
        System.out.println(LocalDateTimeUtil.beginOfDay(now).format(formatter));
        System.out.println(LocalDateTimeUtil.endOfDay(now.plusDays(2)).format(formatter));
    }

}
