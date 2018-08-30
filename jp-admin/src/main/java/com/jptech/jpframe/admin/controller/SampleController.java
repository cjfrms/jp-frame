package com.jptech.jpframe.admin.controller;

import com.jptech.jpframe.admin.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SampleController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/api/test")
    @ResponseBody
    public List<Map> test(){
        return sampleService.getSampleData();
    }

}
