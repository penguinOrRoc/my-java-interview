package com.penguin.spring.CycleDepency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceB {
    @Autowired
    private ServiceA serviceA;

    public void testB(){
        System.out.println("ServiceB_testB()");

    }
}


