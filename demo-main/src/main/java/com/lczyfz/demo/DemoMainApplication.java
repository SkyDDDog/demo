package com.lczyfz.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableWebMvc
@EnableSwagger2
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class}, scanBasePackages = "com.lczyfz.*")
//@EnableAutoConfiguration
@EnableCaching
@EnableScheduling
@MapperScan("com.lczyfz")
public class DemoMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoMainApplication.class, args);
    }
}
