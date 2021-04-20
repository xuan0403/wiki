package com.zhuanye.wiki.service;

import com.zhuanye.wiki.domain.Test;
import com.zhuanye.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource  /*jdk自带*/
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}
