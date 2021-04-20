package com.zhuanye.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


//启动日志
//打印自定义配置信息，打印欢迎页，测试地址，或者提示性方案等等
@SpringBootApplication
public class WikiApplication {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(WikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        //日志文件打印启动成功
        LOG.info("启动成功！！");
        //打印端口地址、端口号
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));

    }
}
