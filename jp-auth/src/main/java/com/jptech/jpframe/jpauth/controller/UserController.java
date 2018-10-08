package com.jptech.jpframe.jpauth.controller;

import com.jptech.jpframe.jpauth.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    SampleService sampleService;

    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public List<Map> test(){
        return sampleService.getSample();
    }

}
