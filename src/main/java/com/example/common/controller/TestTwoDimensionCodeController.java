package com.example.common.controller;

import com.example.common.utils.ChartGraphicsUtil;
import com.example.common.utils.ZipUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * description: 二维码生成控制器
 *
 * @author:cxs
 * @date: 2019/2/21 15:33
 */
@Api(value = "二维码")
@RestController
@RequestMapping("twoDimensionCode")
public class TestTwoDimensionCodeController extends BaseController {

  @Value("${codeImg.path}")
  private String codeImgPath;
  @Value("${codeImg.save-zip-path}")
  private String saveZipPath;

  @ApiOperation(value = "二维码测试", tags = "二维码")
  @GetMapping(value = "/queryBBMemberList")
  @ResponseBody
  public void queryBBMemberList(HttpServletRequest req, HttpServletResponse response) {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");

    String text = "张三";
    String pngName = "李四";
    String bridgeName = "王麻子";
    String chargePerson = "赵六";
    String mobile = "123456789010";
    String remark = "OJBK";
    try {
      int width = 300;
      int height = 300;
      String format = "png";
      Hashtable hints = new Hashtable();
      hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
      hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
      hints.put(EncodeHintType.MARGIN, 2);

      BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
      String filePath = codeImgPath + pngName + ".png";
      Path file = new File(filePath).toPath();
      MatrixToImageWriter.writeToPath(bitMatrix, format, file);
      ChartGraphicsUtil.graphicsGeneration("HuoK", bridgeName, pngName, chargePerson, mobile, remark, filePath);


      FileOutputStream outputStream = new FileOutputStream(new File(saveZipPath + "/二维码.zip"));
      ZipUtils.toZip(codeImgPath, outputStream, true);
      File zipOutXlsxFile = new File(saveZipPath + "/二维码.zip");
      FileInputStream zipOutFileInputStream = new FileInputStream(zipOutXlsxFile);

      String name = "构件二维码" + ".zip";
      String fileName = URLEncoder.encode(name, "UTF-8");
      response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

      IOUtils.copy(zipOutFileInputStream, response.getOutputStream());
      response.flushBuffer();
      zipOutFileInputStream.close();

      ZipUtils.delAllFile(codeImgPath);
      ZipUtils.delAllFile(saveZipPath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
