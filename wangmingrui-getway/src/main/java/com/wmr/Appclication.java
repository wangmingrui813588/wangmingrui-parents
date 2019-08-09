package com.wmr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Appclication {
    public static void main(String[] args) {
        SpringApplication.run(Appclication.class,args);
    }
    @RequestMapping("serverhealth")
    public String serverhealth(){
        System.out.println("=========gateway server check health is ok! ^_^ ========");
        return "ok";
    }
}
