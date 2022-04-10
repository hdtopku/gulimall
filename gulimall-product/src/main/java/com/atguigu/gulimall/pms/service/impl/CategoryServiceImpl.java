package com.atguigu.gulimall.pms.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.pms.dao.CategoryDao;
import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.service.CategoryBrandRelationService;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.vo.Catalog2Vo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    private final CategoryBrandRelationService categoryBrandRelationService;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<Long> findCategoryPath(Long categoryId) {
        LinkedList<Long> categoryPath = new LinkedList<>();
        findParent(categoryId, categoryPath);
        return categoryPath;
    }

    private void findParent(Long categoryId, LinkedList<Long> categoryPath) {
        if (ObjectUtil.isNotNull(categoryId)) {
            categoryPath.addFirst(categoryId);
            CategoryEntity categoryEntity = this.getById(categoryId);
            if (ObjectUtil.isNotNull(categoryEntity) && categoryEntity.getParentCid() != 0) {
                findParent(categoryEntity.getParentCid(), categoryPath);
            }
        }
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     */
    @Transactional
    @Override
    @CacheEvict(value = {"category"}, key = "'level1Categorys'")
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if (StrUtil.isNotBlank(category.getName())) {
            categoryBrandRelationService.updateCascade(category.getCatId(), category.getName());
        }
    }

    @Override
    @Cacheable(value = {"category"}, key = "'level1Categorys'")
    public List<CategoryEntity> getLevel1Categorys() {
//        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
//        System.out.println("消耗时间：" + (System.currentTimeMillis() - l));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        String s = stringRedisTemplate.opsForValue().get("catalogJson");
        if (StrUtil.isNotBlank(s)) {
            return JSONUtil.toBean(s, new TypeReference<Map<String, List<Catalog2Vo>>>() {}, true);
        }
        Map<String, List<Catalog2Vo>> catalogJsonFromDb = getCatalogFromDbWithRedisLock();
        return catalogJsonFromDb;
    }

    private Map<String, List<Catalog2Vo>> getCatalogFromDbWithRedissonLock() {
        RLock lock = redissonClient.getLock("catalogJson-lock");
        lock.lock();
        Map<String, List<Catalog2Vo>> catalogFromDb;
        try {
            catalogFromDb = getCatalogFromDb();
        } finally {
            lock.unlock();
        }
        return catalogFromDb;
    }

    private Map<String, List<Catalog2Vo>> getCatalogFromDbWithRedisLock() {
//        占分布式锁，去redis占坑
//        Boolean myLock = stringRedisTemplate.opsForValue().setIfAbsent("mylock", "world");
        String uuid = UUID.fastUUID().toString();
        Boolean myLock = stringRedisTemplate.opsForValue().setIfAbsent("mylock", uuid, 30, TimeUnit.SECONDS);
        if (BooleanUtil.isTrue(myLock)) {
//            加锁成功
//            stringRedisTemplate.expire("mylock", 30, TimeUnit.SECONDS);
//            if (uuid.equals(stringRedisTemplate.opsForValue().get("mylock"))) {
//                stringRedisTemplate.delete("mylock");
//            }
//            上面的解锁还可能删掉其他线程的说，可采用lua解锁：http://redis.cn/commands/set.html
            Map<String, List<Catalog2Vo>> catalogFromDb;
            try {
                catalogFromDb = getCatalogFromDb();
            } finally {
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), ListUtil.toList("mylock"), uuid);
            }
            return catalogFromDb;
        } else {
//            加锁失败，自旋：休眠100ms，再执行
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatalogFromDbWithRedisLock();
        }
    }

    private Map<String, List<Catalog2Vo>> getCatalogFromDbWithLocalLock() {
//        未进入synchronized的本地进程，监听并阻塞，等待已进入synchronized的那1个进程执行完synchronized中的代码，并释放锁
        synchronized (this) {
            return getCatalogFromDb();
        }
    }

    private Map<String, List<Catalog2Vo>> getCatalogFromDb() {
        String s = stringRedisTemplate.opsForValue().get("catalogJson");
        if (StrUtil.isNotBlank(s)) {
            return JSONUtil.toBean(s, new TypeReference<Map<String, List<Catalog2Vo>>>() {
            }, true);
        }
        System.out.println("查询了数据库");
//        查出所有分类
        List<CategoryEntity> allEntities = this.baseMapper.selectList(new QueryWrapper<>());

        List<CategoryEntity> level1Categorys = this.getParent_cid(allEntities, 0L);
        Map<String, List<Catalog2Vo>> catalogJsonFromDb = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            List<CategoryEntity> level2Categorys = this.getParent_cid(allEntities, v.getCatId());
            return level2Categorys.stream().map(l2 -> {
                List<CategoryEntity> categoryEntities = this.getParent_cid(allEntities, l2.getCatId());
                List<Object> l3Collect = categoryEntities.stream().map(l3 -> new Catalog2Vo.Catalog3Vo(
                        l2.getCatId().toString(), l3.getCatId().toString(), l3.getName())).collect(Collectors.toList());
                return new Catalog2Vo(v.getCatId().toString(), l3Collect, l2.getCatId().toString(), l2.getName());
            }).collect(Collectors.toList());
        }));
        stringRedisTemplate.opsForValue().set("catalogJson", JSONUtil.toJsonStr(catalogJsonFromDb));
        return catalogJsonFromDb;
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> allEntities, Long parentCatId) {
        return allEntities.stream().filter(item -> item.getParentCid().equals(parentCatId)).collect(Collectors.toList());
    }

}