package com.fst.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author：fst
 * @data：2021/8/17 19:44
 * @info：
 */
@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class AppServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(AppServiceMain.class,args);
    }
}
