package com.jptech.jpframe.jpauth;

import com.jptech.jpframe.jpauth.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SampleService {

    @Autowired
    SampleMapper sampleMapper;

    public List<Map> getSample(){
        return sampleMapper.getSample();
    }
}
