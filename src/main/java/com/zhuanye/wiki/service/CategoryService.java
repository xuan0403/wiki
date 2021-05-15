package com.zhuanye.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuanye.wiki.domain.Category;
import com.zhuanye.wiki.domain.CategoryExample;
import com.zhuanye.wiki.mapper.CategoryMapper;
import com.zhuanye.wiki.req.CategoryQueryReq;
import com.zhuanye.wiki.req.CategorySaveReq;
import com.zhuanye.wiki.resp.CategoryQueryResp;
import com.zhuanye.wiki.resp.PageResp;
import com.zhuanye.wiki.util.CopyUtil;
import com.zhuanye.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private static final Logger LOG= LoggerFactory.getLogger(CategoryService.class);

    @Resource  /*jdk自带*/
    private CategoryMapper categoryMapper;

    @Resource  /*jdk自带*/
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();//内部类

        //分页功能=查总数+查当前页数据
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo=new PageInfo<>(categoryList);

        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        List<CategoryQueryResp> respList=new ArrayList<>();
        for (Category category : categoryList) {
            //CategoryQueryResp categoryResp = new CategoryQueryResp();
           /* categoryResp.setId(category.getId());*/  //此写法较为麻烦
            //BeanUtils.copyProperties(category,categoryResp);  //BeanUtils是spring提供的一种较为简单的方法，从category拷贝到categoryResp，实现对象的复制
           //对象复制
            //CategoryQueryResp categoryResp = CopyUtil.copy(category, CategoryQueryResp.class);
            //respList.add(categoryResp);
        }

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /*保存*/
    public void save(CategorySaveReq req){
        Category category=CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //使用雪花算法自己生成一个id
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        }
        else {
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /*删除*/
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
