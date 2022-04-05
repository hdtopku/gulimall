package com.atguigu.gulimall;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class GulimallMemberApplicationTests {

    @Test
    public void test() {
        String s = Md5Crypt.md5Crypt("123456".getBytes());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        passwordEncoder.matches("123456", encode);
    }

}
