package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.domain.Ebook;
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


    @GetMapping("/list")
    public List<Ebook> list(){
        return ebookService.list();
    }

}
