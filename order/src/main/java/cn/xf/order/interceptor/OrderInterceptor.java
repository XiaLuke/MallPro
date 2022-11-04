package cn.xf.order.interceptor;

import cn.xf.common.constant.AuthVerifyConstant;
import cn.xf.common.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OrderInterceptor implements HandlerInterceptor {
    /**
     * 当前模块共享
     */
    public static ThreadLocal<MemberResponseVo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 释放指定请求路径
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 1.查询订单状态请求（在仓库模块中远程调用）
        boolean match = antPathMatcher.match("/order/order/status/**", uri);
        // 2.支付成功回调请求
        boolean match1 = antPathMatcher.match("/payed/notify", uri);
        // 满足直接放行
        if (match || match1) {
            return true;
        }

        // 判断用户是否登录，没有登录跳转登录
        MemberResponseVo loginInfo = (MemberResponseVo) request.getSession().getAttribute(AuthVerifyConstant.LOGIN_USER);
        if (loginInfo != null) {
            threadLocal.set(loginInfo);
            return true;
        } else {
            request.getSession().setAttribute("msg", "请先进行登录");
            response.sendRedirect("http://auth.mall.xyz/login.html");
            return false;
        }
    }
}