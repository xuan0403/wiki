package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.domain.Ebook;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
@RequestMapping("/ebook")
//接口
public class EbookController {

    @Resource
    private EbookService ebookService;


    /*@GetMapping("/list")
    public List<Ebook> list(){
        return ebookService.list();
    }*/

    @GetMapping("/list")
    public CommonResp list(){
        CommonResp<List<Ebook>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        List<Ebook> list= ebookService.list();
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }

}
