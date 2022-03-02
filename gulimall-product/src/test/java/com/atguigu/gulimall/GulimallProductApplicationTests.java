//package com.atguigu.gulimall;
//
//import com.atguigu.gulimall.mbg.entity.PmsBrand;
//import com.atguigu.gulimall.mbg.service.IPmsBrandService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//public class GulimallProductApplicationTests {
//
//    @Autowired
//    IPmsBrandService iPmsBrandService;
//
//    @Test
//    void contextLoads() {
//        PmsBrand brand = new PmsBrand();
//        brand.setName("华为");
//        brand.setBrandId(1L);
//        brand.setDescript("华为品牌😄");
//        iPmsBrandService.save(brand);
////        brand.setName("华为");
//        PmsBrand brand1 = iPmsBrandService.getById(1);
//        System.out.println("保存成功"+brand1);
//
//    }
//
//}
