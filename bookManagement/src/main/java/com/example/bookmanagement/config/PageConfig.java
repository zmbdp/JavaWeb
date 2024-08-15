
package com.example.bookmanagement.config;

import com.example.bookmanagement.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class PageConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")
                .excludePathPatterns( "/admin/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/pic/**")
                .excludePathPatterns( "/error/**")
                .excludePathPatterns("/**/*.html")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/**/login")
                .excludePathPatterns("/favicon.ico");
    }
}

