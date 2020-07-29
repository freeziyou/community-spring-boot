package com.freeziyou.newcoder.config;

import com.freeziyou.newcoder.annotation.LoginRequired;
import com.freeziyou.newcoder.controller.interceptor.LoginRequiredInterceptor;
import com.freeziyou.newcoder.controller.interceptor.LoginTicketInterceptor;
import com.freeziyou.newcoder.entity.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Dylan Guo
 * @date 7/29/2020 11:14
 * @description TODO
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.css", "/**/*.jpeg");
        registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.css", "/**/*.jpeg");
    }
}
