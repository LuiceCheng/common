package com.example.common.oss.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/25 10:00
 */
@Configuration
public class OSSConfig {

  @Value("${aliyun.oss.endpoint}")
  private String endPoint;

  @Value("${aliyun.oss.accessKeyId}")
  private String accessKeyId;

  @Value("${aliyun.oss.secretAccessKey}")
  private String secretAccessKey;

  /**
   * 初始化OSS客户端
   *
   * @return oss客户端
   */
  @Bean
  public OSSClient ossClient() {
    return new OSSClient(endPoint, accessKeyId, secretAccessKey);
  }
}
