package com.jptech.jpframe.jpauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

/*    @GetMapping("/user")
    public Principal user(Principal user){
        return user;
    }*/

    @GetMapping(value = "/user")
    public String user(){
        return "XXXXX";
    }
}
