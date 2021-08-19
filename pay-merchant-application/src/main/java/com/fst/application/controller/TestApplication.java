package com.fst.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：fst
 * @data：2021/8/12 14:35
 * @info：
 */

@RestController
public class TestApplication {
    @Autowired
    DiscoveryClient discoveryClient;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    LoadBalancerClient loadBalancerClient;


    // @Value("${fst.value}")
    // String fst;
    // @RequestMapping("/m1")
    // public String m1(){
    //     System.out.println("----->"+discoveryClient.getInstances("merchant-application").toString());
    //
    //     System.out.println(fst);
    //     return "cc";
    // }
}
