package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.req.EbookQueryReq;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.service.EbookService;
import com.zhuanye.wiki.req.EbookSaveReq;
import com.zhuanye.wiki.resp.EbookQueryResp;
import com.zhuanye.wiki.resp.PageResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
@RequestMapping("/ebook")
//接口
public class EbookController {

    @Resource
    private EbookService ebookService;


    /*@GetMapping("/list")
    public List<EbookQueryReq> list(){
        return ebookService.list();
    }*/

    /*@GetMapping("/list")
    public CommonResp list(String name){  //参数名字可以自动映射，如果参数名字很多，需要将所有参数封装成一个类
        CommonResp<List<EbookQueryReq>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        List<EbookQueryReq> list= ebookService.list(name);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }*/

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req){  //需要将所有参数封装成一个类
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        PageResp<EbookQueryResp> list= ebookService.list(req);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }

    //一般保存类的、更新类的接口，都用PostMapping,Post请求要用RequestBody注解
    @PostMapping("/save")
    public CommonResp save(@RequestBody  EbookSaveReq req){  //需要将所有参数封装成一个类
        CommonResp resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        ebookService.save(req);
        return resp;
    }


    //电子书删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){  //需要将所有参数封装成一个类
        CommonResp resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        ebookService.delete(id);
        return resp;
    }

}
