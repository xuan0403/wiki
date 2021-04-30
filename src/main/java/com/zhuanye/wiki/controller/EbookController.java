package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.req.EbookReq;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.resp.EbookResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
@RequestMapping("/ebook")
//接口
public class EbookController {

    @Resource
    private EbookService ebookService;


    /*@GetMapping("/list")
    public List<EbookReq> list(){
        return ebookService.list();
    }*/

    /*@GetMapping("/list")
    public CommonResp list(String name){  //参数名字可以自动映射，如果参数名字很多，需要将所有参数封装成一个类
        CommonResp<List<EbookReq>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        List<EbookReq> list= ebookService.list(name);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }*/

    @GetMapping("/list")
    public CommonResp list(EbookReq req){  //需要将所有参数封装成一个类
        CommonResp<PageResp<EbookResp>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        PageResp<EbookResp> list= ebookService.list(req);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }

}
