package cn.xf.order.config;

import cn.xf.order.interceptor.OrderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author XF
 * @date 2022/10/15
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    @Autowired
    OrderInterceptor orderInterceptor;

    /**
     * 添加拦截器
     *  --用于用户结算商品
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 使用自定义拦截器并监控所有请求
        registry.addInterceptor(orderInterceptor).addPathPatterns("/**");
    }
}
