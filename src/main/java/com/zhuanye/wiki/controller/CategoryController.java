package com.zhuanye.wiki.controller;

import com.zhuanye.wiki.req.CategoryQueryReq;
import com.zhuanye.wiki.req.CategorySaveReq;
import com.zhuanye.wiki.resp.CommonResp;
import com.zhuanye.wiki.resp.CategoryQueryResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

//@Controller //用来返回页面
@RestController  //一般用来返回字符串，因为采用前后端分离，所以一般是用不到@Controller的
@RequestMapping("/category")
//接口
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /*@GetMapping("/list")
    public List<CategoryQueryReq> list(){
        return categoryService.list();
    }*/

    /*@GetMapping("/list")
    public CommonResp list(String name){  //参数名字可以自动映射，如果参数名字很多，需要将所有参数封装成一个类
        CommonResp<List<CategoryQueryReq>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        List<CategoryQueryReq> list= categoryService.list(name);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }*/

    @GetMapping("/list")
    //@Valid表示此组参数开启校验规则，对应的规则就是里面的这些注解
    public CommonResp list(@Valid CategoryQueryReq req){  //需要将所有参数封装成一个类
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        PageResp<CategoryQueryResp> list= categoryService.list(req);
        resp.setContent(list);            //在CommonResp类里success默认的是true
        return resp;
    }

    //一般保存类的、更新类的接口，都用PostMapping,Post请求要用RequestBody注解
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody  CategorySaveReq req){  //需要将所有参数封装成一个类
        CommonResp resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        categoryService.save(req);
        return resp;
    }


    //电子书删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){  //需要将所有参数封装成一个类
        CommonResp resp = new CommonResp<>();//这里的凡星是实际返回业务数据的类型，及content的类型
        categoryService.delete(id);
        return resp;
    }

}
