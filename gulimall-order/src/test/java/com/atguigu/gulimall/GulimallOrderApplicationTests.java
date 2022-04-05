package com.atguigu.gulimall;
import java.util.Date;

import com.atguigu.gulimall.oms.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * The type Gulimall order application tests.
 */
@SpringBootTest
@Slf4j
public class GulimallOrderApplicationTests {
    @Resource
    private AmqpAdmin amqpAdmin;
    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 1、如何创建Exchange、Queue、Binding
     */
    @Test
    void createExchange() {
        DirectExchange directExchange = new DirectExchange("hello-java-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        // 声明发送优惠券的消息队列（Direct类型的exchange）
        log.info("Exchange[{}]创建成功", "hello-java-exchange");
    }

    /**
     * Create queue.
     */
    @Test
    void createQueue() {
        amqpAdmin.declareQueue(new Queue("hello-java-queue"));
        log.info("Queue[{}]创建成功", "hello-java-queue");
    }
    @Test
    void createBinding() {
//        String destination：目的地
//        DestinationType destinationType：目的地类型
//        String exchange：交换机
//        String routingKey：路由键
//			@Nullable Map<String, Object> arguments：参数
        Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE,
                "hello-java-exchange", "hello-java",
                null);
        amqpAdmin.declareBinding(binding);
        log.info("hello-java-exchange与hello-java-queue绑定成功");
    }
    @Test
    void sendMessage() {
        for(int i = 0; i<10;i++) {
            OrderReturnReasonEntity entity = new OrderReturnReasonEntity();
            entity.setId(0L);
            entity.setName("哈哈");
            entity.setSort(0);
            entity.setStatus(0);
            entity.setCreateTime(new Date());
            rabbitTemplate.convertAndSend("hello-java-exchange", "hello-java", entity);
            log.info("消息发送完成");
        }
    }
}
