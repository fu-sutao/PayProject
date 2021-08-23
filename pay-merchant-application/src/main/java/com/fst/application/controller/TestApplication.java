package com.fst.application.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    @Autowired
    OSS oss;
    @ApiOperation("测试")
    @PostMapping("/upload2")
    public String upload2(@RequestParam("file") MultipartFile multipartFile){
        try {
            InputStream inputStream = multipartFile.getInputStream();
            //获取上传文件名称
            String fullename =multipartFile.getOriginalFilename();
            //截取文件扩展名
            String ext = fullename.substring(fullename.lastIndexOf("."));
            //自定义文件名称
            String fileName = System.currentTimeMillis()+ext;
            // 组合阿里云OSS上传参数  依次为 存储空间名，文件名（可以包括文件夹）,文件流
            // 注意对象存储没有文件夹概念，如果要区分文件可以再文件名加/  eg:/2021/04/16/202111222555.png
            PutObjectRequest putObjectRequest = new PutObjectRequest("test-pay-manager",fileName,inputStream);
            // 上传
            PutObjectResult putObjectResult = oss.putObject(putObjectRequest);

            return "test-pay-manager.oss-cn-beijing.aliyuncs.com/"+fileName;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


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
