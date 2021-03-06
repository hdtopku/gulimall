package com.atguigu.gulimall.oms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.common.constant.OrderConstant;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.oms.dao.OrderDao;
import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.atguigu.gulimall.oms.feign.CartFeignService;
import com.atguigu.gulimall.oms.feign.MemberFeignService;
import com.atguigu.gulimall.oms.feign.WareFeignService;
import com.atguigu.gulimall.oms.interceptor.LoginUserInterceptor;
import com.atguigu.gulimall.oms.service.OrderService;
import com.atguigu.gulimall.oms.to.OrderCreateTo;
import com.atguigu.gulimall.oms.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service("orderService")
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    private final MemberFeignService memberFeignService;
    private final CartFeignService cartFeignService;
    private final StringRedisTemplate redisTemplate;
    private final WareFeignService wareFeignService;


    private ThreadLocal<OrderSubmitVo> submitVoThreadLocal = new ThreadLocal<>();
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageUtils(page);
    }

    @SneakyThrows
    @Override
    public OrderConfirmVo confirmOrder() {
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        OrderConfirmVo orderConfirmVo = new OrderConfirmVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        log.error("????????????" + Thread.currentThread().getId());
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
//        ???????????????????????????????????????
            log.error("address?????????" + Thread.currentThread().getId());
            List<MemberAddressVo> addresses = memberFeignService.getAddresses(memberRespVo.getId());
            orderConfirmVo.setAddress(addresses);
        });
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
//        ?????????????????????????????????????????????
            log.error("cart?????????" + Thread.currentThread().getId());
            List<OrderItemVo> userCartItems = cartFeignService.getUserCartItems();
            orderConfirmVo.setItems(userCartItems);
        });
//        ??????????????????
        Integer integration = memberRespVo.getIntegration();
        orderConfirmVo.setIntegration(integration);
// TODO ???????????? orderToken
        String token = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId(), token, 30, TimeUnit.MINUTES);
        orderConfirmVo.setOrderToken(token);
        CompletableFuture.allOf(addressFuture, cartFuture).get();
        return orderConfirmVo;
    }

//    @GlobalTransactional
    @Transactional
    @Override
    public SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo) {
        submitVoThreadLocal.set(submitVo);
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        SubmitOrderResponseVo response = new SubmitOrderResponseVo();
        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
//        ????????????
        String orderToken = submitVo.getOrderToken();
        String script = "if redis.call('get', KEYS[1]==ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//        ?????????????????????Long?????????0 ?????????1 ?????????????????????????????????
        Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class),
                Collections.singletonList(OrderConstant.USER_ORDER_TOKEN_PREFIX + memberRespVo.getId()), orderToken);
        if (1L == result) {
//            ??????????????????

        }
        return response;
    }

    @Override
//    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void buy(Long skuId) {

        wareFeignService.addSkuStock();
        OrderEntity byId = this.getById(1L);
        byId.setTotalAmount(byId.getTotalAmount().add(new BigDecimal(1)));
        this.updateById(byId);
        log.info("ok");
        int i = 10 / 0;
    }

    private OrderCreateTo createOrderCreateTo() {
        OrderCreateTo orderCreateTo = new OrderCreateTo();
//        1??????????????????
        String orderSn = IdWorker.getTimeId();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(orderSn);
        OrderSubmitVo orderSubmitVo = submitVoThreadLocal.get();
        submitVoThreadLocal.remove();
//        2????????????????????????????????????????????????
//        3???????????????????????????
//        ????????????
        return orderCreateTo;
    }
}