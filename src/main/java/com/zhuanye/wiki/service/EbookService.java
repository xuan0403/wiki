package com.zhuanye.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuanye.wiki.domain.Ebook;
import com.zhuanye.wiki.domain.EbookExample;
import com.zhuanye.wiki.mapper.EbookMapper;
import com.zhuanye.wiki.req.EbookReq;
import com.zhuanye.wiki.resp.EbookResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.util.CopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG= LoggerFactory.getLogger(EbookService.class);

    @Resource  /*jdk自带*/
    private EbookMapper ebookMapper;

    public PageResp<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();//内部类
        //动态sql
        if(!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");//根据name查找
        }
        //分页功能=查总数+查当前页数据
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo=new PageInfo<>(ebookList);

        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<EbookResp> respList=new ArrayList<>();
        for (Ebook ebook : ebookList) {
            //EbookResp ebookResp = new EbookResp();
           /* ebookResp.setId(ebook.getId());*/  //此写法较为麻烦
            //BeanUtils.copyProperties(ebook,ebookResp);  //BeanUtils是spring提供的一种较为简单的方法，从ebook拷贝到ebookResp，实现对象的复制
           //对象复制
            //EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
            //respList.add(ebookResp);
        }

        //列表复制
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
        PageResp<EbookResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }
}
