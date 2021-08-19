package com.fst.application.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author：fst
 * @data：2021/8/19 9:42
 * @info：
 */

@Slf4j
@Service
public class SmsService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RestTemplate restTemplate;

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.effectiveTime}")
    private String effectiveTime;
    //获取验证码
    public String getSendSms(String mobile) {
        //验证码过期时间 600 秒  10 分钟
        String url = smsUrl + "/generate?name=sms&effectiveTime=" + effectiveTime;
        log.info("调用短信微服务发送验证码：url:" + url);
        Map<String, Object > body = new HashMap<>();
        body.put("mobile", mobile);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //封装请求参数
        HttpEntity entity = new HttpEntity(body, httpHeaders);
        ResponseEntity<Map> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        Map responseMap = forEntity.getBody();
        log.info("获取验证码：[{}]", responseMap);
        //取出body中的result数据
        if (responseMap == null || responseMap.get("result") == null) {
            throw new RuntimeException("发送验证码出错");
        }
        Map resultMap = (Map) responseMap.get("result");
        String value = resultMap.get("key").toString();
        return value;
    }

    //检验验证码
    public void checkVerifiyCode(String key, String Code) {
        String url = smsUrl + "/verify?name=sms&verificationCode=" + Code + "&verificationKey=" + key;
        Map responseMap = null;
        try {
            //请求校验验证码
            ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST,
                    HttpEntity.EMPTY, Map.class);
            responseMap = exchange.getBody();
            log.info("校验验证码，响应内容：{}", JSON.toJSONString(responseMap));
        } catch (RestClientException e) {
            e.printStackTrace();
            log.info(e.getMessage(), e);
            throw new RuntimeException("验证码错误");
        }
        if (responseMap == null || responseMap.get("result") == null || !(Boolean)
                responseMap.get("result")) {
            throw new RuntimeException("验证码错误");
        }
    }
}
