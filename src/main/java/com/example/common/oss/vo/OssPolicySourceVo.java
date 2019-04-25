package com.example.common.oss.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * description:OSS策略原始对象VO
 *
 * @author:cxs
 * @date: 2019/4/25 10:32
 */
public class OssPolicySourceVo implements Serializable {
  private static final long serialVersionUID = 1747789283837156819L;
  /**
   * 限制，约束
   */
  private List<Object> conditions;
  /**
   * 策略过期时间，设置某个时间前可以使用该策略，
   */
  @JSONField(format = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private Date expiration;

  public List<Object> getConditions() {
    return conditions;
  }

  public void setConditions(List<Object> conditions) {
    this.conditions = conditions;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  /**
   * 存储域策略
   */
  public class BucketPolicy {

    /**
     * 存储域
     */
    private String bucket;

    public String getBucket() {
      return bucket;
    }

    public void setBucket(String bucket) {
      this.bucket = bucket;
    }
  }
}
