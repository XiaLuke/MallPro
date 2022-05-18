package cn.xf.warehousing.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("cn.xf.warehousing.dao")
@Configuration
public class MybatisConfig {
    //引入分页插件
    @Bean
    public MybatisPlusInterceptor paginationInnerInterceptor(){
        MybatisPlusInterceptor myBatisInterceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        myBatisInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        return myBatisInterceptor;
    }
}
