package com.example.common.controller;

import com.example.common.oss.util.OSSUtil;
import com.example.common.oss.vo.OssFileVo;
import com.example.common.utils.FileDownloadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/25 10:38
 */
@Api(value = "OSS测试")
@RestController
@RequestMapping("oss")
public class TestOSSController {

  Logger logger = LoggerFactory.getLogger(TestOSSController.class);

  @Autowired
  private OSSUtil ossUtil;


  @ApiOperation(value ="123", tags = "oss")
  @PutMapping("test/save")
  @ResponseBody
  public OssFileVo saveOss(HttpServletRequest request){
    List<MultipartFile> files = new ArrayList<>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
    for (Object key : multiFileMap.keySet()) {
      files = multiFileMap.get(key.toString());
    }
    OssFileVo upload = null;
    if(CollectionUtils.isNotEmpty(files)){
      upload = ossUtil.upload(files.get(0));
    }
    return upload;
  }

  /**
   * 测试下载excel
   * @param response
   * @param filePath
   */
  @ApiOperation(value ="123", tags = "oss")
  @GetMapping("test/download")
  @ResponseBody
  public void download(HttpServletResponse response,String filePath,String originalFilename){
    String ossSingRealUrl = ossUtil.getOssSingRealUrl(filePath);
    logger.info(ossSingRealUrl);
    FileDownloadUtils.downloadFromUrl(response,ossSingRealUrl,originalFilename);
  }
}
