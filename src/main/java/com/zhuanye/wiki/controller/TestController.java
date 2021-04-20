package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.domain.Test;
import com.zhuanye.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
//接口
public class TestController {

    //@Value("${test.hello:TEST}")里TEST是默认值
    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private TestService testService;

    /*
    * 请求方式：GET(发送请求),POST（接收请求）,PUT（修改请求）,DELETE（删除请求）
    * /user？id=1
    * /user/1
    * @GetMapping 方法指接受get请求
    * @PostMapping，浏览器不能访问post请求
    * @PutMapping
    * @DeleteMapping
    * @RequestMapping(value="/user/1",method = RequestMethod.GET)  //注解，表示这个接口只支持GET请求方式
    * */
    //@RequestMapping("/hello")  //注解，表示这个接口支持所有的请求方式
    @GetMapping("/hello")
    public String hello(){

        return "Hello World!" +testHello;
    }

    @PostMapping("/hello/post")
   /* public String helloPost(Map<String,String> map){*/  //参数接收不到
    public String helloPost(String name){

        return "Hello World!Post" +name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

}
