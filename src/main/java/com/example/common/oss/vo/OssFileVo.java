package com.example.common.oss.vo;

import java.io.Serializable;

/**
 * description:OSS 文件VO
 *
 * @author:cxs
 * @date: 2019/4/25 10:21
 */
public class OssFileVo implements Serializable {
  private static final long serialVersionUID = 864835469857965351L;

  /**
   * 原始文件名称
   */
  private String originalFilename;

  /**
   * 存储在OSS 服务器的文件URL
   */
  private String ossFileUrl;

  /**
   * 存储在OSS 服务器的文件名称
   */
  private String ossFileName;

  public String getOssFileName() {
    return ossFileName;
  }

  public void setOssFileName(String ossFileName) {
    this.ossFileName = ossFileName;
  }

  public String getOriginalFilename() {
    return originalFilename;
  }

  public void setOriginalFilename(String originalFilename) {
    this.originalFilename = originalFilename;
  }

  public String getOssFileUrl() {
    return ossFileUrl;
  }

  public void setOssFileUrl(String ossFileUrl) {
    this.ossFileUrl = ossFileUrl;
  }
}
