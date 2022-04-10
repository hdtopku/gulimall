package com.atguigu.gulimall.wms;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxh
 * @createTime 2022-04-09 18:32:36
 */
@Configuration
public class StockMqConfig {
    private static final String STOCK_EXCHANGE = "stock-event-exchange";
    /**
     * 监听才能执行以下的bean方法
     * @param message
     */
    @RabbitListener(queues = "stock.release.stock.queue")
    public void handle(Message message) {

    }
    @Bean
    public Exchange exchange() {
        return new TopicExchange(STOCK_EXCHANGE, true, false);
    }
    /**
     * 绑定生产队列：stock.delay.queue，路由键：stock.locked
     * @return
     */
    @Bean
    public Binding stockLockBinding() {
        return new Binding("stock.delay.queue", Binding.DestinationType.QUEUE
                , STOCK_EXCHANGE, "stock.locked", null);
    }
    /**
     * 死信队列：stock.delay.queue，路由键：release.order
     * @return
     */
    @Bean
    public Queue stockDelayQueue() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", STOCK_EXCHANGE);
        map.put("x-dead-letter-routing-key", "release.order");
//        2分钟
        map.put("x-message-ttl", 120000);
        return new Queue("stock.delay.queue", true, false, false, map);
    }
    /**
     * 消费队列：stock.release.stock.queue
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue("stock.release.stock.queue", true, false, false);
    }
    /**
     * 绑定消费队列：stock.release.stock.queue，路由键：stock.release.#
     * @return
     */
    @Bean
    public Binding stockReleaseBinding() {
        return new Binding("stock.release.stock.queue", Binding.DestinationType.QUEUE
                , STOCK_EXCHANGE, "stock.release.#", null);
    }
}
