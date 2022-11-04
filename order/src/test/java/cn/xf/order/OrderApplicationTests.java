package cn.xf.order;

import cn.xf.order.entity.OrderEntity;
import cn.xf.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class OrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 创建交换机
     */
    @Test
    void createExchange() {
        // 声明一个指定类型的交换机
        // 交换机名字，交换机模式（是否持久化），是否自动删除，其他参数
        DirectExchange directExchange = new DirectExchange("java-direct-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("[{}]exchange create success", "java-direct-exchange");
    }

    /**
     * 创建队列
     */
    @Test
    void createQueue() {
        // 队列名字，是否持久化，是否排他（只能一个使用者连接），是否自动删除，其他参数
        Queue queue = new Queue("java-direct-queue", true, false, false);
        amqpAdmin.declareQueue(queue);
        log.info("[{}]队列创建成功", "java-direct-queue");
    }

    /**
     * 绑定交换机和队列
     */
    @Test
    void createBinding() {
        // 目的地，目的地类型，交换机，路由键，其他参数
        Binding binding = new Binding("java-direct-queue",
                Binding.DestinationType.QUEUE,
                "java-direct-exchange",
                "java-direct",
                null);
        amqpAdmin.declareBinding(binding);
    }

    /**
     * 发送消息
     */
    @Test
    void sendMessage() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                OrderReturnReasonEntity orderReturnReasonEntity = new OrderReturnReasonEntity();
                orderReturnReasonEntity.setName("什么东西");
                orderReturnReasonEntity.setStatus(i);
                rabbitTemplate.convertAndSend("java-direct-exchange", "java-direct", orderReturnReasonEntity);
            } else {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setOrderSn(String.valueOf(UUID.randomUUID()));
                rabbitTemplate.convertAndSend("java-direct-exchange", "java-direct", orderEntity);
            }

        }
    }

    /**
     * 得到消息
     * <p>
     * 接收的消息类型为Message，其中包括了详细信息
     * <p>
     * 请求参数可包含以下一个或多个：
     *
     * @param message 消息
     * @param reason  message中的消息体
     * @param channel 当前传输数据的通道
     * @throws InterruptedException 中断异常
     */
    @RabbitListener(queues = {"java-direct-queue"})
    void getMessage1(Message message, OrderReturnReasonEntity reason, Channel channel) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("收到消息1:" + reason.toString());
    }

    @RabbitListener(queues = {"java-direct-queue"})
    void getMessage2(OrderEntity reason) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("收到消息2:" + reason.toString());
    }
}
