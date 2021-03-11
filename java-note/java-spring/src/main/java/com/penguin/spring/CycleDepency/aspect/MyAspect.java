package com.penguin.spring.CycleDepency.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    @Pointcut("execution(* com.penguin.spring.CycleDepency.service.ServiceA.testA() )")
    private void log(){
    }

    @Before("log()")
    public void invokeLogBefore(){
        System.out.println("前置日志~");


    }
    @After("log()")
    public void invokeLogEnd(){
        System.out.println("后置日志~");


    }
}


