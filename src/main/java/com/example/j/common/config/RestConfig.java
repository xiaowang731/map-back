//package com.example.j.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class RestConfig {
//
//    @Bean
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(20000);
//        factory.setReadTimeout(180000);
//        return new RestTemplate(factory);
//    }}