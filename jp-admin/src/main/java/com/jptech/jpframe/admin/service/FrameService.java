package com.jptech.jpframe.admin.service;

import com.jptech.jpframe.admin.mapper.FrameMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class FrameService<M extends FrameMapper> {

    @Autowired
    M mapper ;

}
