package com.jptech.jpframe.admin.mapper;

import com.jptech.jpframe.core.comm.mapper.FrameMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SampleMapper extends FrameMapper {

    public List<Map> getSampleData(Map param);

    public List<Map> getSample();

    public void insertTest();

    public void insertAnother();
}
