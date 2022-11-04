package cn.xf.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过Spring创建消息队列
 *
 * @author XF
 * @date 2022/10/20
 */
@Configuration
public class MyMqCreateConfig {
    // --------------------- 当前配置用于用户下单 ---------------------

    /**
     * 订单延迟队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("x-dead-letter-exchange", "order-exchange");
        requestParam.put("x-dead-letter-routing-key", "order.release");
        // 消息过期时间 1分钟
        requestParam.put("x-message-ttl", 60000);
        //String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
        Queue queue = new Queue("order.delay.queue", true, false, false, requestParam);
        return queue;
    }

    /**
     * 订单释放队列
     *
     * @return {@link Queue}
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.queue", true, false, false, null);
    }

    /**
     * 交换机
     *
     * @return {@link Exchange}
     */
    @Bean
    public Exchange orderExchange() {
        // 为了精确匹配队列，使用topic模式
        return new TopicExchange("order-exchange", true, false);
    }

    @Bean
    public Binding delayBinding() {
        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-exchange",
                "order.delay.order",
                null);
    }

    @Bean
    public Binding releaseBinding() {
        return new Binding("order.release.queue",
                Binding.DestinationType.QUEUE,
                "order-exchange",
                "order.release.order",
                null);
    }

    /**
     * 订单库存释放绑定
     *
     * @return {@link Binding}
     */
    @Bean
    public Binding orderStockReleaseBinding(){
        return new Binding("stock.release.queue",
                Binding.DestinationType.QUEUE,
                "order-exchange",
                "order.release.other.#",
                null);
    }
}
