package com.software.blogserver.config;

import com.software.blogserver.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new JWTInterceptor())
                //添加拦截路径
                .addPathPatterns("/**");
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }
}
