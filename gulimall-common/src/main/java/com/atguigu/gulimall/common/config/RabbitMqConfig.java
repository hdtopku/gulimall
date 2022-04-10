package com.atguigu.gulimall.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * The type Rabbit mq config.
 *
 * @author lxh
 * @createTime 2022 -04-03 21:35:37
 */
@Configuration
@Slf4j
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMqConfig {
    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
//        RabbitMqConfig对象创建完成以后，执行该方法
        /**
         * 设置正确抵达交换机确认回调
         * @param correlationData 当前消息的唯一关联数据（这个是消息的唯一id）
         * @param ack 消息是否成功收到，只要消息抵达broker，ack就为true
         * @param cause 失败的原因
         */rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.info("correlationData：{}, ack：{}, cause：{}", correlationData, ack, cause));
        /**
         * 设置消息未抵达队列的确认回调(例：发送消息时指定一个错误的routingKey[hello-java2])
         * @param message 投递失败的消息详细信息
         * @param replyCode 回复的状态码
         * @param replyText 回复的文本内容
         * @param exchange 当时这个消息发送给哪个交换机
         * @param routingKey 当时这个消息用哪个路由键
         */rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}",
                        message, replyCode, replyText, exchange, routingKey));
    }
}

class RabbitMqSerializerConfig {

    /**
     * 消息的序列化，不能放在RabbitMqConfig类中，不然会依赖冲突
     * ┌─────┐
     * |  rabbitTemplate defined in class path resource [org/springframework/boot/autoconfigure/amqp/RabbitAutoConfiguration$RabbitTemplateConfiguration.class]
     * ↑     ↓
     * |  rabbitTemplateConfigurer defined in class path resource [org/springframework/boot/autoconfigure/amqp/RabbitAutoConfiguration$RabbitTemplateConfiguration.class]
     * ↑     ↓
     * |  rabbitMqConfig defined in file [/Users/lxh/Desktop/coding/gulimall/gulimall-common/target/classes/com/atguigu/gulimall/common/config/RabbitMqConfig.class]
     * └─────┘
     * Message converter message converter.
     *
     * @return the message converter
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}