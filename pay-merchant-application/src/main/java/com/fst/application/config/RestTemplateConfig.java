package com.fst.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author：fst
 * @data：2021/8/19 16:47
 * @info：注入bean
 */
public class RestTemplateConfig {
    // @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    // }
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory());
        List<HttpMessageConverter<?>> httpMessageConverter = restTemplate.getMessageConverters();
        httpMessageConverter.set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(httpMessageConverter);
        return restTemplate;
    }
}
