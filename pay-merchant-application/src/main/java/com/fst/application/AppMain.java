package com.fst.application;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author：fst
 * @data：2021/8/12 11:15
 * @info：
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@MapperScan("com.fst.service.mapper")
public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class,args);
    }
}
