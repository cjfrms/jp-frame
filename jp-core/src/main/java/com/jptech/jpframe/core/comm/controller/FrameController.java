package com.jptech.jpframe.core.comm.controller;

import com.jptech.jpframe.core.comm.service.FrameService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FrameController<S extends FrameService> {

    @Autowired
    public S service;
}
