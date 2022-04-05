package com.atguigu.gulimall.web;

import cn.hutool.core.lang.UUID;
import com.atguigu.gulimall.common.constant.AuthServerConstant;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.vo.Catalog2Vo;
import lombok.RequiredArgsConstructor;
import org.redisson.api.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lxh
 * @createTime 2022-03-19 22:09:38
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final CategoryService categoryService;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model, HttpSession session) {
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();
        model.addAttribute("categorys", categoryEntities);
//        使用springSession，就无需像model中塞loginUser数据，故下面的代码可以注释了
//        model.addAttribute("loginUser", session.getAttribute(AuthServerConstant.LOGIN_USER));
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        RLock lock = redissonClient.getLock("mylock");
        lock.lock();
        lock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("加锁成功，执行业务代码..."+Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁..."+Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    @GetMapping("write")
    @ResponseBody
    public String writeValue() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        RLock rLock = lock.writeLock();
        String s = "init";
        try {
//          加写锁
            rLock.lock();
            System.out.println("写加锁成功");
            s = UUID.fastUUID().toString();
            TimeUnit.SECONDS.sleep(30);
            stringRedisTemplate.opsForValue().set("writeValue", s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }

    @GetMapping("read")
    @ResponseBody
    public String readValue() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");
        RLock rLock = lock.readLock();
//        加读锁
        rLock.lock();
        String s = "init";
        try {
            s = UUID.fastUUID().toString();
            TimeUnit.SECONDS.sleep(30);
            stringRedisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("读释放锁");
        }
        return s;
    }

    @GetMapping("minus")
    @ResponseBody
    public String minus() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("mybucket");
        park.acquire(); // 获取一个信号，获取一个值，占一个车位
//        boolean b = park.tryAcquire(); // 尝试获取信号量，成功则b=true，未获得则b=false
        return "ok";
    }
    @GetMapping("plus")
    @ResponseBody
    public String plus() {
        RSemaphore park = redissonClient.getSemaphore("mybucket");
        park.release(); //释放一个车位
        return "ok";
    }

    @GetMapping("lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.trySetCount(2);
        door.await();
        return "放假了";
    }
    @GetMapping("go")
    @ResponseBody
    public String go() {
        RCountDownLatch door = redissonClient.getCountDownLatch("door");
        door.countDown();
        return "走了一个班";
    }
}
