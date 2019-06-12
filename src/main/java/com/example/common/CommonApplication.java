package com.example.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@SpringBootApplication(exclude = { RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.example.common.dao")
@EnableRedisHttpSession
public class CommonApplication {

    public static void main(String[] args) {
        System.out.printf("启动成功");
        SpringApplication.run(CommonApplication.class, args);
    }

}
