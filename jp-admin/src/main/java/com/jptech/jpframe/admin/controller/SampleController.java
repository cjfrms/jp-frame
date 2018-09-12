package com.jptech.jpframe.admin.controller;

import com.jptech.jpframe.admin.service.SampleService;
import com.jptech.jpframe.core.comm.controller.FrameController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SampleController extends FrameController<SampleService> {

    @GetMapping("/api/test")
    @ResponseBody
    public List<Map> test(){
        //service.getSampleData();
        //sampleService.getSample();
        return service.getSampleData();
    }

    @GetMapping("/api/save")
    @ResponseBody
    public String testSave() throws Exception {
        service.saveTest();

        return "ok";
    }

    @GetMapping("/api/saveTrans")
    @ResponseBody
    public String testTrans() throws Exception {
        service.saveMulti();
        return "ok";
    }

}
