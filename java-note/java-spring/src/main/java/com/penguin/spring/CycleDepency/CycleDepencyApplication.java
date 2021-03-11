package com.penguin.spring.CycleDepency;

import com.penguin.spring.CycleDepency.service.ServiceA;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CycleDepencyApplication {
    public static void main(String[] args){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        ServiceA serviceA = applicationContext.getBean(ServiceA.class);
        System.out.println("ServiceA:"+serviceA);
        serviceA.testA();
//        ServiceC ServiceC = applicationContext.getBean(ServiceC.class);
//        System.out.println("ServiceC:"+ServiceC);

    }
}


