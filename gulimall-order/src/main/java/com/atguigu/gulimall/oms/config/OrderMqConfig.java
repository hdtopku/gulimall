package com.atguigu.gulimall.oms.config;

import com.atguigu.gulimall.oms.entity.OrderEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh
 * @createTime 2022-04-09 13:09:33
 */
@Configuration
public class OrderMqConfig {
    private static final String ORDER_EXCHANGE= "order-event-exchange";
    @RabbitListener(queues = "order.release.order.queue")
    public void Listener(OrderEntity entity, Channel channel, Message message) throws IOException {
        System.out.println("收到过期订单信息，准备关闭订单：" + entity.getOrderSn());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(ORDER_EXCHANGE, true, false);
    }
    /**
     * 绑定生产队列：order.delay.queue，路由键：order.create.order
     * @return
     */
    @Bean
    public Binding orderCreateOrderBinding() {
        return new Binding("order.delay.queue", Binding.DestinationType.QUEUE,
                ORDER_EXCHANGE, "order.create.order", null);
    }
    /**
     * 死信队列：order.delay.queue，路由键：order.release.order
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", ORDER_EXCHANGE);
        args.put("x-dead-letter-routing-key", "order.release.order");
        args.put("x-message-ttl", 60000);
        return new Queue("order.delay.queue", true, false, false, args);
    }
    /**
     * 消费队列：order.release.order.queue
     * @return
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }
    /**
     * 绑定消费队列：order.release.order.queue，路由键：order.release.order
     * @return
     */
    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding("order.release.order.queue", Binding.DestinationType.QUEUE,
                ORDER_EXCHANGE, "order.release.order", null);
    }
}
