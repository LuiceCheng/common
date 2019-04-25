package com.example.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/4/25 14:14
 */
public class FileDownloadUtils {

  public static byte[] readInputStream(InputStream inputStream){
    try {
      byte[] buffer = new byte[1024];
      int len = 0;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      while((len = inputStream.read(buffer)) != -1) {
        bos.write(buffer, 0, len);
      }
      bos.close();
      return bos.toByteArray();
    }catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public static void downloadFromUrl(HttpServletResponse response,String urlStr, String originalFilename){
    try {
      String type = originalFilename.substring(originalFilename.lastIndexOf("."));
      URL url = new URL(urlStr);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      //设置超时间为10秒
      conn.setConnectTimeout(100000);
      //防止屏蔽程序抓取而返回403错误
      conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

      //得到输入流
      InputStream inputStream = conn.getInputStream();
      //获取自己数组
      byte[] getData = readInputStream(inputStream);

      String fileName=new String(originalFilename.getBytes("GB2312"),"ISO8859-1");
      response.setHeader("Content-disposition", "attachment; filename="+fileName);
      handleContentType(response,type);
      OutputStream fos = response.getOutputStream();
      fos.write(getData);
      if(fos!=null){
        fos.close();
      }
      if(inputStream!=null){
        inputStream.close();
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private static void handleContentType(HttpServletResponse response,String type){
    switch (type){
      case ".xml": response.setContentType("text/xml"); break;
      case ".txt": response.setContentType("text/plain"); break;
      case ".ppt": response.setContentType("application/x-ppt"); break;
      case ".png": response.setContentType("application/x-png"); break;
      case ".mp3": response.setContentType("audio/mp3"); break;
      case ".mp4": response.setContentType("video/mpeg4"); break;
      case ".jsp": response.setContentType("text/html"); break;
      case ".js": response.setContentType("application/x-javascript"); break;
      case ".jpg": response.setContentType("application/x-jpg"); break;
      case ".img": response.setContentType("application/x-img"); break;
      case ".html": response.setContentType("text/html"); break;
      case ".gif": response.setContentType("image/gif"); break;
      case ".doc": response.setContentType("application/msword"); break;
      case ".xls": response.setContentType("application/vnd.ms-excel"); break;
      case ".xlsx": response.setContentType("application/msexcel"); break;
    }

  }
}
