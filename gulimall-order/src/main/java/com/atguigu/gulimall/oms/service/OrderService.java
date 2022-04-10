package com.atguigu.gulimall.oms.service;

import com.atguigu.gulimall.oms.vo.OrderConfirmVo;
import com.atguigu.gulimall.oms.vo.OrderSubmitVo;
import com.atguigu.gulimall.oms.vo.SubmitOrderResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.oms.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author $ {author}
 * @email $ {email}
 * @date 2022 -04-04 00:23:02
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * Query page page utils.
     *
     * @param params the params
     * @return the page utils
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页返回需要的数据
     * Confirm order order confirm vo.
     *
     * @return the order confirm vo
     */
    OrderConfirmVo confirmOrder();

    SubmitOrderResponseVo submitOrder(OrderSubmitVo submitVo);

    /**
     * 仅测试seata全局事务的方法
     * @param skuId
     */
    void buy(Long skuId);
}

