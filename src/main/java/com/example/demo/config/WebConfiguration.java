package com.example.demo.config;

import com.example.demo.interceptor.LoginRequestInterceptor;
import com.example.demo.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class WebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private LoginRequestInterceptor loginRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequestInterceptor).addPathPatterns("/create");
        super.addInterceptors(registry);
    }
}
