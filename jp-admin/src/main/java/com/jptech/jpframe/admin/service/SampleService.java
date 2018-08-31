package com.jptech.jpframe.admin.service;

import com.jptech.jpframe.admin.comm.annotation.TargetDataSource;
import com.jptech.jpframe.admin.mapper.SampleMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleService extends FrameService<SampleMapper>{

    @TargetDataSource(id = "ds-master")
    public List<Map> getSampleData(){
        Map con = new HashMap();
        con.put("TRIGGER_NAME","SampleBatch");
        con.put("TRIGGER_GROUP","sample");
        return this.mapper.getSampleData(con);
    }

    //@TargetDataSource(id = "ds-slaveX")
    public List<Map> getSample(){
        return this.mapper.getSample();
    }
}
