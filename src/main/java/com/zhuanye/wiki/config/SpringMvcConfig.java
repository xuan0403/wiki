package com.zhuanye.wiki.config;

import com.zhuanye.wiki.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//拦截器还需要增加一个配置类
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    //把拦截器注入进来
    @Resource
    LogInterceptor logInterceptor;

    //增加一个拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                //表示针对所有的请求，所有接口都要去打印接口耗时,excludePathPatterns排除请求，不拦截
                .addPathPatterns("/**").excludePathPatterns("/login");


    }
}
