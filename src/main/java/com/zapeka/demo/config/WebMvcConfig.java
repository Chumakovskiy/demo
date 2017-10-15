package com.zapeka.demo.config;

import com.zapeka.demo.interceptor.QuestionCountInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Volodymyr on 13.10.2017.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(questionCountInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public QuestionCountInterceptor questionCountInterceptor() {
        return new QuestionCountInterceptor();
    }
}
