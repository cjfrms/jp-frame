package com.jptech.jpframe.core.comm.controller;

import com.jptech.jpframe.core.comm.entity.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalContollerAdvisor {
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception e){
        e.printStackTrace();;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
    }
}
