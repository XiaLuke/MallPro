package cn.xf.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * feign拦截器
 * 对feign设置请求头信息
 *
 * @author XF
 * @date 2022/10/15
 */
@Configuration
public class MyFeignInterceptor {
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // 获取第一次进来的request数据
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    HttpServletRequest request = requestAttributes.getRequest();
                    if (request != null) {
                        // 将从浏览器请求的cookid设置到整个服务中
                        String cookies = request.getHeader("Cookie");
                        requestTemplate.header("Cookie", cookies);
                    }

                }
            }
        };
        return requestInterceptor;
    }
}
