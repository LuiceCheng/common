package com.example.common.oss.util;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.HmacSHA1Signature;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.example.common.oss.vo.OssFileVo;
import com.example.common.oss.vo.OssPolicySourceVo;
import com.example.common.oss.vo.OssPolicyVo;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/25 10:18
 */
@Component
public class OSSUtil {
  Logger logger = LoggerFactory.getLogger(FileUpload.class);

  @Resource
  private OSSClient ossClient;

  @Value("${aliyun.oss.bucketName}")
  private String bucketName;

  //分隔符--点
  private static final String SEPARATE_POINT = ".";

  private static final String CHART_SET_UTF8 = "UTF-8";

  @Async
  public void upload(String bucketName, String newFileName, MultipartFile file) {
    try {
      long start = System.currentTimeMillis();
      ossClient.putObject(bucketName, newFileName, file.getInputStream());
      logger.info("上传成功，用时：{}", System.currentTimeMillis() - start);
    } catch (IOException ex) {
      logger.error("上传文件[{}]出错，{}", file.getOriginalFilename(), ex.getMessage());
    }
  }

  public List<OssFileVo> upload(String bucketName, List<MultipartFile> files) {
    List<OssFileVo> ossFileList = new ArrayList<>();

    if (null == files) {
      return ossFileList;
    }

    for (MultipartFile file : files) {
      String originalFilename = file.getOriginalFilename();
      String suffix = StringUtils.substringAfterLast(originalFilename, SEPARATE_POINT);
      String newFilename =
          UUID.randomUUID().toString().replaceAll("-", "") + SEPARATE_POINT + suffix;

      upload(bucketName, newFilename, file);
      StringBuilder sbUrl = new StringBuilder();
      sbUrl.append("http://");
      sbUrl.append(bucketName);
      sbUrl.append(".");
      sbUrl.append(ossClient.getEndpoint().getHost());
      sbUrl.append("/");
      sbUrl.append(newFilename);
      logger.info("上传结果：{}", getOssSingRealUrl(newFilename));

      OssFileVo ossFileVO = new OssFileVo();
      ossFileVO.setOriginalFilename(originalFilename);
      ossFileVO.setOssFileUrl(sbUrl.toString());
      ossFileVO.setOssFileName(newFilename);
      ossFileList.add(ossFileVO);
    }
    return ossFileList;
  }

  public OssFileVo upload(String bucketName, MultipartFile file) {
    OssFileVo ossFile = new OssFileVo();
    if (null == file) {
      return ossFile;
    }
    String originalFilename = file.getOriginalFilename();
    String suffix = org.apache.commons.lang.StringUtils.substringAfterLast(originalFilename, SEPARATE_POINT);
    String newFilename =
        UUID.randomUUID().toString().replaceAll("-", "") + SEPARATE_POINT + suffix;

    upload(bucketName, newFilename, file);
    StringBuilder sbUrl = new StringBuilder();
    sbUrl.append("http://");
    sbUrl.append(bucketName);
    sbUrl.append(".");
    sbUrl.append(ossClient.getEndpoint().getHost());
    sbUrl.append("/");
    sbUrl.append(newFilename);
    logger.info("上传结果：{}", getOssSingRealUrl(newFilename));

    ossFile = new OssFileVo();
    ossFile.setOriginalFilename(originalFilename);
    ossFile.setOssFileUrl(sbUrl.toString());
    ossFile.setOssFileName(newFilename);
    return ossFile;
  }

  public OssFileVo upload(MultipartFile file) {
    return upload(bucketName, file);
  }

  public List<OssFileVo> upload(List<MultipartFile> files){
    return upload(bucketName, files);
  }


  public String getOssSingRealUrl(String fileSaveName) {
    if (StringUtils.isEmpty(fileSaveName)) {
      return "";
    }
    Date expiration = new Date(new Date().getTime() + 100000000 * 1000); //生成失效时间
    return ossClient.generatePresignedUrl(bucketName, fileSaveName, expiration).toString();
  }

  public OssPolicyVo ossPolicy(String bucketName) {
    OssPolicySourceVo ossPolicySourceVO = new OssPolicySourceVo();
    ossPolicySourceVO.setExpiration(DateUtils.addMinutes(new Date(), 30));

    List<Object> conditions = new ArrayList<>();

    //文件大小限制
    List<Object> contentLengthRange = new ArrayList<>();
    contentLengthRange.add("content-length-range");
    contentLengthRange.add(0);
    contentLengthRange.add(104857600);
    conditions.add(contentLengthRange);

    //存储域限制
    OssPolicySourceVo.BucketPolicy bucketPolicy = ossPolicySourceVO.new BucketPolicy();
    bucketPolicy.setBucket(bucketName);
    conditions.add(bucketPolicy);

    ossPolicySourceVO.setConditions(conditions);

    String policy = JSON.toJSONString(ossPolicySourceVO);
    logger.info("policy:{}",policy);
    String base64policy = BinaryUtil.toBase64String(policy.getBytes(Charset.defaultCharset()));

    String accessKeyId = ossClient.getCredentialsProvider().getCredentials().getAccessKeyId();
    String accessKeySecret = ossClient.getCredentialsProvider().getCredentials()
        .getSecretAccessKey();
    String signature = HmacSHA1Signature.create().computeSignature(accessKeySecret, base64policy);

    //最终策略
    OssPolicyVo ossPolicyVO = new OssPolicyVo();
    ossPolicyVO.setBucket(bucketName);
    ossPolicyVO.setEndpoint(ossClient.getEndpoint().getHost());
    ossPolicyVO.setFormAction("http://" + bucketName + "." + ossClient.getEndpoint().getHost());
    ossPolicyVO.setBase64policy(base64policy);
    ossPolicyVO.setAccessKeyId(accessKeyId);
    ossPolicyVO.setSignature(signature);

    return ossPolicyVO;
  }
}
