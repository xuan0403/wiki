package com.zhuanye.wiki.service;

import com.zhuanye.wiki.domain.Demo;
import com.zhuanye.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

    @Resource  /*jdk自带*/
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}
