package com.zhuanye.wiki.service;

import com.zhuanye.wiki.domain.Ebook;
import com.zhuanye.wiki.domain.EbookExample;
import com.zhuanye.wiki.mapper.EbookMapper;
import com.zhuanye.wiki.req.EbookReq;
import com.zhuanye.wiki.resp.EbookResp;
import com.zhuanye.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource  /*jdk自带*/
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();//内部类
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

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
        return list;
    }
}
