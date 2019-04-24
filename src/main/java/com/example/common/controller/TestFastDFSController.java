package com.example.common.controller;

import com.example.common.fastDFS.client.FileResponseData;
import com.example.common.fastDFS.services.FastDFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/23 11:37
 */
@Controller
@RequestMapping("fastdfs")
public class TestFastDFSController {

  @Autowired
  private FastDFSService fastDFSService;

  /**
   * 图片下载demo
   *
   * @param filePath
   * @param response
   */
  @RequestMapping("download")
  @ResponseBody
  public void downloadFile(String filePath, HttpServletResponse response) {
    try {
      fastDFSService.downloadFile(filePath, response);
    } catch (Exception e) {

    }
  }

  /**
   * 图片上传demo
   *
   * @param request
   */
  @RequestMapping(value = "/upload/file/sample")
  @ResponseBody
  public FileResponseData uploadFileSample(HttpServletRequest request) {
    List<MultipartFile> files = new ArrayList<>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
    for (Object key : multiFileMap.keySet()) {
      files = multiFileMap.get(key.toString());
    }
    return fastDFSService.uploadFileSample(files.get(0), request);
  }
}
