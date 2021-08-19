package com.fst.service.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author：fst
 * @data：2021/8/17 20:52
 * @info：添加分页插件  和性能监控插件
 */

@Configuration
@MapperScan("com.fst.**.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 启用性能分析插件
     */
    // @Bean
    // public PerformanceInterceptor performanceInterceptor(){
    //     return new PerformanceInterceptor();
    // }
}
