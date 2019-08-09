package com.wmr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SSO-SERVER启动类
 */
@SpringBootApplication
@EnableJpaAuditing
@RestController
@EntityScan(basePackages = {"com.wmr.entity.**"})
public class SsoServer {
    public static void main(String[] args) {
        SpringApplication.run(SsoServer.class,args);
    }

    @RequestMapping("health")
    public String health(){
        System.out.println("==========SSO-SERVER  ok!==========");
        return "ok";
    }
}

