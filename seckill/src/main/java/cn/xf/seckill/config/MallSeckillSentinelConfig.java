package cn.xf.seckill.config;

import org.springframework.context.annotation.Configuration;


/**
 * 自定义阻塞返回方法
 *
 * @author XF
 * @date 2022/10/24
 */
@Configuration
public class MallSeckillSentinelConfig {

    /*public GulimallSeckillSentinelConfig() {

        WebCallbackManager.setUrlBlockHandler(new UrlBlockHandler() {
            @Override
            public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws IOException {
                R error = R.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JSON.toJSONString(error));

            }
        });

    }
*/
}
