package cn.xf.cart.interceptor;

import cn.xf.cart.to.UserInfoTo;
import cn.xf.common.constant.AuthVerifyConstant;
import cn.xf.common.constant.CartConstant;
import cn.xf.common.vo.MemberResponseVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 购物车拦截器
 * <p>
 * 在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求
 * <p>
 * 拦截器必须实现SpringMvc的HandlerInterceptor
 *
 * @author XF
 * @date 2022/10/10
 */
public class CartInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    /**
     * 目标方法执行前
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 封装将要传输给Controller的对象
        UserInfoTo userInfo = new UserInfoTo();

        HttpSession session = request.getSession();
        // 获取session当前用户登录的信息
        MemberResponseVo loginInfo = (MemberResponseVo) session.getAttribute(AuthVerifyConstant.LOGIN_USER);
        // 用户登录了
        if (loginInfo != null) {
            userInfo.setUserId(loginInfo.getId());
        }
        // request中获取cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if (name.equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                    userInfo.setUserKey(cookie.getValue());
                    userInfo.setTempUser(true);
                }
            }
        }
        // 如果用户第一次使用且没有登录，分配一个临时用户
        if (StringUtils.isEmpty(userInfo.getUserKey())) {
            String uuid = UUID.randomUUID().toString();
            userInfo.setUserKey(uuid);
        }
        threadLocal.set(userInfo);
        return true;
    }

    /**
     * 后置拦截器
     * 前置处理器中判断了用户是否邓丽，没有登录的设置了user-key，此时需要将这个值设置到浏览器中的cookie中
     *
     * @param request      请求
     * @param response     响应
     * @param handler      处理程序
     * @param modelAndView 模型和视图
     * @throws Exception 异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userinfo = threadLocal.get();
        if (!userinfo.isTempUser()) {
            // 将user-key存入cookie中
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userinfo.getUserKey());
            // 设置作用域和过期时间
            cookie.setDomain("mall.xyz");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            // 设置到浏览器中
            response.addCookie(cookie);
        }
    }
}
