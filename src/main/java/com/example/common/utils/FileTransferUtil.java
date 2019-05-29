package com.example.common.utils;

import com.example.common.fastDFS.client.FileResponseData;
import com.example.common.fastDFS.services.FastDFSService;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * description:
 *  将oss文件转存fastdfs文件，并一对一对应病害关系
 * @author:cxs
 * @date: 2019/4/30 11:10
 */
public class FileTransferUtil {
  private static String savePath = "D:\\data\\tempSave";

  //1.先根据att_path从oss下载文件
  public static FileResponseData downloadFromOSS(String fileSavePath, FastDFSService fastDFSService){
    String ossSingRealUrl = fileSavePath;
    if(!fileSavePath.contains("http")){
      ossSingRealUrl = OssClientUtil.getOssSingRealUrl(fileSavePath);
    }
    //1.1生成文件到暂存目录
    File file = createFile(ossSingRealUrl, savePath);
    try {
      if(null == file){
        return null;
      }
      FileInputStream fileInputStream = new FileInputStream(file);
      MultipartFile multipartFile = new MockMultipartFile(file.getName(),file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
      //2.上传文件
      FileResponseData fileResponseData = fastDFSService.uploadSample(multipartFile, null, false);
      return fileResponseData;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if(null != file){
        file.delete();
      }
    }
    return null;
  }

  public static File createFile(String fileRoute, String savePath){
    String fileName = getFilename(fileRoute);
    try {
      URL url = new URL(fileRoute);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      if (null == conn) {
        return null;
      }
      //设置超时间为3秒
      conn.setConnectTimeout(10 * 1000);
      //防止屏蔽程序抓取而返回403错误
      conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

      //得到输入流
      InputStream inputStream = conn.getInputStream();
      if (null == inputStream) {
        return null;
      }
      //获取自己数组
      byte[] getData = readInputStream(inputStream);

      //文件保存位置
      File saveDir = new File(savePath);
      if (!saveDir.exists()) {
        saveDir.mkdir();
      }
      File file = new File(saveDir + File.separator + fileName);
      FileOutputStream fos = new FileOutputStream(file);
      fos.write(getData);
      if (fos != null) {
        fos.close();
      }
      if (inputStream != null) {
        inputStream.close();
      }
      return file;
    } catch (ConnectException connectException) {
      return null;
    }catch (SocketTimeoutException exception){
      return null;
    } catch (FileNotFoundException e){
      return null;
    } catch (IOException arg24) {
      return null;
    } finally {

    }
  }

  /**
   * 从输入流中获取字节数组
   * @param inputStream
   * @return
   * @throws IOException
   */
  public static  byte[] readInputStream(InputStream inputStream) throws IOException {
    byte[] buffer = new byte[1024];
    int len = 0;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    while((len = inputStream.read(buffer)) != -1) {
      bos.write(buffer, 0, len);
    }
    bos.close();
    return bos.toByteArray();
  }

  /**
   * 根据oss全路径获取文件名
   * @param filename
   * @return
   */
  public static String getFilename(String filename) {
    String s = filename.substring(filename.lastIndexOf("/")+1, filename.indexOf("?"));
    return s;
  }
}
