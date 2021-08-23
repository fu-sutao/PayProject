package com.fst.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：fst
 * @data：2021/8/19 16:50
 * @info：测试获取验证码
 */

@Slf4j
public class SmsTest {
    @Autowired
    RestTemplate restTemplate;
    @Before
    public void init(){
        // restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        // List<HttpMessageConverter<?>> httpMessageConverter = restTemplate.getMessageConverters();
        // httpMessageConverter.set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // restTemplate.setMessageConverters(httpMessageConverter);
        restTemplate = new RestTemplate();
    }


    @Test
    public void testGetSmsCode() {
        String url = "http://127.0.0.1:56085/sailing/generate?effectiveTime=60&name=sms";
        String phone = "13081936214";
        //请求体
        Map<String, Object > body = new HashMap();
        body.put("mobile", phone);
        //请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置数据格式为json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        //封装请求参数
        HttpEntity entity = new HttpEntity(body, httpHeaders);
        ResponseEntity<Map> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        Map responseMap = forEntity.getBody();
        log.info("获取验证码：【{}】", responseMap);
        //取出body中的result数据
        if (responseMap != null || responseMap.get("result") != null) {
            Map resultMap = (Map) responseMap.get("result");
            String value = resultMap.get("key").toString();
            System.out.println(value);
        }
    }


}
