package com.jptech.jpframe.core.comm.service;

import com.jptech.jpframe.core.comm.mapper.FrameMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FrameService<M extends FrameMapper>  {

    @Autowired
    public M mapper;
}
