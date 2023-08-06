package com.example.finalexamplespring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //모든 요청에 대한 예외 등록
        registry.addMapping("/**")
                //예외로 동작할 주소 지정
                .allowedOrigins("http://192.168.0.2:3000")
                .allowedOrigins("http://localhost:3001")
                //허용될 요청방식
                .allowedMethods("*")
                //허용될 요청 헤더
                .allowedHeaders("*")
                //인증에 관한 정보 허용
                .allowCredentials(true)
                //타임아웃 시간 설정
                .maxAge(3600);
    }
}
