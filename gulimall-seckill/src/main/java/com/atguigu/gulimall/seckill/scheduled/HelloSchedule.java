package com.atguigu.gulimall.seckill.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lxh
 * @createTime 2022-04-10 21:49:44
 */
@Component
@EnableScheduling
@Slf4j
@EnableAsync
public class HelloSchedule {
    @Scheduled(cron = "* * * * * ?")
    @Async
    public void hello() {
        log.info("hello");
    }
}
