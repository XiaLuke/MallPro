package cn.xf.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author XF
 * <p>
 * EnableDiscoveryClient：开启服务的注册发现功能
 * MapperScan：dao扫描
 */
@MapperScan("cn.xf.product.dao")
@EnableFeignClients(basePackages = "cn.xf.product.feign")
@SpringBootApplication
@EnableDiscoveryClient
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
