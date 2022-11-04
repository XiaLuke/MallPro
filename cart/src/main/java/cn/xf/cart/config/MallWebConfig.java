package cn.xf.cart.config;

import cn.xf.cart.interceptor.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 购物车中web配置文件
 *
 * @author XF
 * @date 2022/10/11
 */
@Configuration
public class MallWebConfig implements WebMvcConfigurer {
    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 指定使用定义的拦截器和拦截所有方法
        registry.addInterceptor(new CartInterceptor()).addPathPatterns("/**");
    }
}
