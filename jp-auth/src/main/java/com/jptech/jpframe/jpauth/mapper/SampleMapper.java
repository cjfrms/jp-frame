package com.jptech.jpframe.jpauth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SampleMapper {
    public List<Map> getSample();
}
