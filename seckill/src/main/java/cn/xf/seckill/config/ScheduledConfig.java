package cn.xf.seckill.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 定时任务配置
 *
 * @author XF
 * @date 2022/10/24
 */
@EnableAsync
@EnableScheduling
@Configuration
public class ScheduledConfig {

}
