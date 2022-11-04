package cn.xf.order.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitMqConfig {

    // @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 解决rabbitTemplate循环依赖问题
     *
     * @param connectionFactory 连接工厂
     * @return {@link RabbitTemplate}
     */
    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    /**
     * 自定义消息转换器
     * 将数据转为json格式
     *
     * @return {@link MessageConverter}
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate，用来保证消息可达
     *
     * PostConstruct，MyRabbitMqConfig创建完后调用该方法
     * */
    // @PostConstruct
    public void initRabbitTemplate(){
        // 设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            /**
             * 确认
             *
             * @param correlationData 当前消息的唯一关联数据（消息唯一id）,在发送消息时可指定
             * @param b               消息是否成功接收
             * @param s               失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {

            }
        });

        // 设置消息抵达队列的确认回调，只要消息没有投递给指定队列，触发失败回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * 返回消息
             *
             * @param returnedMessage 返回消息
             *                      Message message;    失败消息详细信息
             *                      int replyCode;      回复状态码
             *                      String replyText;   恢复文本内容
             *                      String exchange;    消息交换机
             *                      String routingKey;  消息使用的路由键
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("失败消息："+returnedMessage.getMessage()+
                        "状态码："+returnedMessage.getReplyCode()+
                        "回复文本内容："+returnedMessage.getReplyText()+
                        "交换机："+returnedMessage.getExchange()+
                        "路由键："+returnedMessage.getRoutingKey());
            }
        });
    }
}
