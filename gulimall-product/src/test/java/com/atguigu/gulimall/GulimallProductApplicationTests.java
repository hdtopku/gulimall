package com.atguigu.gulimall;

import com.atguigu.gulimall.pms.dao.AttrGroupDao;
import com.atguigu.gulimall.common.vo.SpuItemAttrGroupVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = {GulimallProductApplication.class})
public class GulimallProductApplicationTests {
    @Resource
    private AttrGroupDao attrGroupDao;
    @Test
    void test() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(3L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
    }
    @Test
    void contextLoads() {
//        stringRedisTemplate.opsForHash();  // key为hash的map
//        stringRedisTemplate.opsForList();  // list
//        stringRedisTemplate.opsForSet();  // set
//        stringRedisTemplate.opsForZSet();  //排序的set
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        ops.set("foo", "bar" + UUID.fastUUID());
//        System.out.println("之前保存的数据是：" + ops.get("foo"));
    }

}
