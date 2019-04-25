package com.example.common.oss.vo;

import java.io.Serializable;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/25 10:29
 */
public class OssPolicyVo implements Serializable {
  private static final long serialVersionUID = 7128214201654058687L;

  /**
   * 表单请求地址
   */
  private String formAction;

  /**
   * 存储域
   */
  private String bucket;

  /**
   * 存储服务器
   */
  private String endpoint;

  /**
   * 访问KEY
   */
  private String accessKeyId;

  /**
   * Base64限制策略
   */
  private String base64policy;

  /**
   * 签名
   */
  private String signature;

  public String getFormAction() {
    return formAction;
  }

  public void setFormAction(String formAction) {
    this.formAction = formAction;
  }

  public String getBucket() {
    return bucket;
  }

  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getAccessKeyId() {
    return accessKeyId;
  }

  public void setAccessKeyId(String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public String getBase64policy() {
    return base64policy;
  }

  public void setBase64policy(String base64policy) {
    this.base64policy = base64policy;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }
}
