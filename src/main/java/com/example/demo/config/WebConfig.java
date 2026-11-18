package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // [11주차] 단계2 : 업로드한파일을주소로보여주는설정
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**") // 주소: /uploads/파일이름,로그인필요
                .addResourceLocations("file:uploads/"); // 실제위치상대경로: 프로젝트폴더/uploads/
    }
}
