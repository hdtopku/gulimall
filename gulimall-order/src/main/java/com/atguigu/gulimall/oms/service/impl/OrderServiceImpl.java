package com.atguigu.gulimall.oms.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.common.vo.MemberRespVo;
import com.atguigu.gulimall.oms.dao.OrderDao;
import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.atguigu.gulimall.oms.feign.CartFeignService;
import com.atguigu.gulimall.oms.feign.MemberFeignService;
import com.atguigu.gulimall.oms.interceptor.LoginUserInterceptor;
import com.atguigu.gulimall.oms.service.OrderService;
import com.atguigu.gulimall.oms.vo.MemberAddressVo;
import com.atguigu.gulimall.oms.vo.OrderConfirmVo;
import com.atguigu.gulimall.oms.vo.OrderItemVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {
    private final MemberFeignService memberFeignService;
    private final CartFeignService cartFeignService;

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
        log.error("主线程：" + Thread.currentThread().getId());
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
//        远程查询所有的收获地址列表
            log.error("address线程：" + Thread.currentThread().getId());
            List<MemberAddressVo> addresses = memberFeignService.getAddresses(memberRespVo.getId());
            orderConfirmVo.setAddress(addresses);
        });
        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
//        远程查询购物车所有选中的购物项
            log.error("cart线程：" + Thread.currentThread().getId());
            List<OrderItemVo> userCartItems = cartFeignService.getUserCartItems();
            orderConfirmVo.setItems(userCartItems);
        });
//        查询用户积分
        Integer integration = memberRespVo.getIntegration();
        orderConfirmVo.setIntegration(integration);
// TODO 防重提交 orderToken
        CompletableFuture.allOf(addressFuture, cartFuture).get();
        return orderConfirmVo;
    }

}