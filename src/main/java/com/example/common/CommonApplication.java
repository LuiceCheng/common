package com.example.common;

import com.example.springcloud.server.service.TestVo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@SpringBootApplication(exclude = { RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.example.common.dao")
public class CommonApplication {

    public static void main(String[] args) {
        System.out.printf("启动成功");
        SpringApplication.run(CommonApplication.class, args);
    }

}
