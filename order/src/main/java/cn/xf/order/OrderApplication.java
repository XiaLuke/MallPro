package cn.xf.order;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 订单应用程序
 *
 *
 * @EnableRabbit 开启rabbitmq
 * @author XF
 * @date 2022/10/13
 */
@SpringBootApplication
@EnableRedisHttpSession
@EnableRabbit
@EnableFeignClients
@MapperScan("cn.xf.order.dao")
// @EnableDiscoveryClient //1.4版本后可以不加
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
