package com.atguigu.gulimall.oms.service.impl;

import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.atguigu.gulimall.oms.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.oms.dao.OrderItemDao;
import com.atguigu.gulimall.oms.entity.OrderItemEntity;
import com.atguigu.gulimall.oms.service.OrderItemService;


@Service("orderItemService")
@RabbitListener(queues = {"hello-java-queue"})
@Slf4j
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    @RabbitHandler
    public void receiveMessage(Message message, OrderReturnReasonEntity content, Channel channel) {
        log.info("接收到消息：" + message+"，内容：" + content);
//        deliveryTag是消息的序号：1，2，3 ...
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            if (deliveryTag % 2 == 0) {
                channel.basicAck(deliveryTag, false);
            } else {
//                broker的消息有三种状态：Ready、Unacked、Total，如果不basicNack或basicAck，消息一直处于Unacked
//                ，不会被删除，也不会重新入队。channel.basicReject(deliveryTag, false)同basicNack，但是不可以批量
                channel.basicNack(deliveryTag, false, true);
            }
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }
    }
    @RabbitHandler
    public void receiveMessage(Message message, OrderEntity content, Channel channel) {

    }

}