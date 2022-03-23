package com.atguigu.gulimall;

import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;


@SpringBootTest(classes = GulimallProductApplicationTests.class)
public class GulimallProductApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
//        stringRedisTemplate.opsForHash();  // key为hash的map
//        stringRedisTemplate.opsForList();  // list
//        stringRedisTemplate.opsForSet();  // set
//        stringRedisTemplate.opsForZSet();  //排序的set
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("foo", "bar" + UUID.fastUUID());
        System.out.println("之前保存的数据是：" + ops.get("foo"));
    }

}
