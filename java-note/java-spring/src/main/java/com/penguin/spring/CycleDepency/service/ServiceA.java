package com.penguin.spring.CycleDepency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {
    @Autowired
    private ServiceB serviceB;
    public void testA(){
        System.out.println("调用了ServiceA的testA()");
    }
}


