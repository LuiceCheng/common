package com.example.common.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  @Bean
  public RedisRepository redisRepository(RedisTemplate<String, String> redisTemplate) {
    return new RedisRepository(redisTemplate);
  }

}
