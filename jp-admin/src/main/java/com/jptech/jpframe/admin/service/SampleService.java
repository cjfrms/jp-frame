package com.jptech.jpframe.admin.service;

import com.jptech.jpframe.admin.comm.persistence.SqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SampleService {

    @Autowired
    SqlManager sqlManager;

    public List<Map> getSampleData(){
        Map con = new HashMap();
        con.put("TRIGGER_NAME","SampleBatch");
        con.put("TRIGGER_GROUP","sample");
        return sqlManager.getResultSet(con,"sampleService.getSampleData");
    }
}
