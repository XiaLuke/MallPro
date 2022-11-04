package cn.xf.warehousing.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过Spring创建消息队列
 *
 * @author XF
 * @date 2022/10/21
 */
@Configuration
public class MyMqCreateConfig {

    /**
     * 使用JSON序列化机制，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    // --------------------- 当前配置用于用户下单得仓库锁定 ---------------------

    /**
     * 延迟队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue stockDelayQueue(){
        Map<String,Object> requestParam = new HashMap<>();
        requestParam.put("x-dead-letter-exchange", "stock-exchange");
        requestParam.put("x-dead-letter-routing-key", "stock.release");
        // 消息过期时间 单位毫秒 30000=30s
        requestParam.put("x-message-ttl", 120000);
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Queue queue = new Queue("stock.delay.queue", true, false, false, requestParam);
        return queue;
    }

    @Bean
    public Queue stockReleaseQueue(){
        return new Queue("stock.release.queue", true, false, false, null);
    }

    /**
     * 交换机
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange stockExchange(){
        // 为了精确匹配队列，使用topic模式
        return new TopicExchange("stock-exchange",true,false);
    }

    @Bean
    public Binding delayBinding(){
        return new Binding("stock.delay.queue",
                Binding.DestinationType.QUEUE,
                "stock-exchange",
                "stock.locked",
                null);
    }

    @Bean
    public Binding releaseBinding(){
        return new Binding("stock.release.queue",
                Binding.DestinationType.QUEUE,
                "stock-exchange",
                "stock.release",
                null);
    }

}
