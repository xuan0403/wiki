package com.zhuanye.wiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
//接口
public class TestController {

    /*
    * 请求方式：GET(发送请求),POST（接收请求）,PUT（修改请求）,DELETE（删除请求）
    * /user？id=1
    * /user/1
    * @GetMapping 方法指接受get请求
    * @PostMapping
    * @PutMapping
    * @DeleteMapping
    * @RequestMapping(value="/user/1",method = RequestMethod.GET)  //注解，表示这个接口只支持GET请求方式
    * */
    //@RequestMapping("/hello")  //注解，表示这个接口支持所有的请求方式
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

}
