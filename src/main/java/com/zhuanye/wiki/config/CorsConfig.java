package com.zhuanye.wiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*配置文件，解决前后端分离常见的跨域报错*/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //表示针对所有的请求地址，针对所有的接口
                .allowedOriginPatterns("*")    //允许来源
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)   //凭证
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）
    }

}
