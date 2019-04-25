package com.example.common.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 根据图片和文字生成自定义图片
 *
 * @author:cxs
 * @date: 2019/2/26 9:25
 */
public class ChartGraphicsUtil {
  private static BufferedImage image;
  private static int imageHeight = 750;//图片高度
  private static int imageWidth = 1050;//图片宽度

  public static void graphicsGeneration(String projectName, String bridgeName,String memberName, String chargeName,String telNumber, String remark,String imgurl) {
    String fullBridgeName = "桥梁名称:"+bridgeName;
    String fullMemberName = "构件名称:"+memberName;
    String fullChargeName = "责任人:"+chargeName;
    String fullTelNumber = "联系电话:"+telNumber;
    String fullMark = "备注信息:";
    int length = remark.length();

    if(StringUtils.isNotEmpty(remark)){
      imageHeight = 750;
    }else {
      imageHeight = 700;
    }
    image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    //设置图片的背景色
    Graphics2D main = image.createGraphics();
    main.setColor(Color.white);
    main.fillRect(0, 0, imageWidth, imageHeight);

    //页面头部
    Graphics title = image.createGraphics();
    //画矩形框
    ((Graphics2D) title).setStroke(new BasicStroke(4f));
    title.setColor(new Color(56, 92, 139));
    title.drawLine(0, 0, imageWidth, 0);//上横
    title.drawLine(imageWidth, 0, imageWidth, imageHeight);//右竖
    title.drawLine(0, imageHeight, imageWidth, imageHeight);//下横
    title.drawLine(0, 0, 0, imageHeight);//左竖

    int r = 0, g = 177, b = 241;
    //设置字体颜色，先设置颜色，再填充内容
    title.setColor(new Color(r, g, b));
    //设置字体
    Font titleFont = new Font("宋体", Font.BOLD, 32);
    title.setFont(titleFont);
    title.drawString("交通基础设施登记卡", 140, 160);
    title.drawString(projectName, 180, 200);

    //插入中间构件二维码图
    Graphics mainPic = image.getGraphics();
    BufferedImage bimg = null;
    try {
      bimg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (bimg != null) {
      int width = bimg.getWidth();
      int height = bimg.getHeight();
      mainPic.drawImage(bimg, 140, 220, width, height, null);
      mainPic.dispose();
    }

    Font btnFont = new Font("宋体", Font.BOLD, 28);
    //信息矩形框X坐标点
    int mesFillRectX = 500;
    //信息矩形框文字X坐标点
    int mesFillRectStrX = 510;
    int mesFillRectYBri = 240;
    int mesFillRectYMem = mesFillRectYBri + 70;
    int mesFillRectYCharge = mesFillRectYMem + 70;
    int mesFillRectYTel = mesFillRectYCharge + 70;
    int mesFillRectYMark = mesFillRectYTel + 70;
    //桥梁信息坐标点
    int mesFillRectBriStrY = 280;
    int mesFillRectMemStrY = mesFillRectBriStrY + 70;
    int mesFillRectChargeStrY = mesFillRectMemStrY + 70;
    int mesFillRectTelStrY = mesFillRectChargeStrY + 70;
    int mesFillRectMarkStrY = mesFillRectTelStrY + 70;
    //信息矩形框宽度
    int mesFillRectWidth = 440;
    //信息矩形框高度
    int mesFillRectHeight = 60;

    Graphics2D btn1 = image.createGraphics();
    btn1.setColor(new Color(r, g, b));
    btn1.fillRect(mesFillRectX, mesFillRectYBri, mesFillRectWidth, mesFillRectHeight);
    btn1.setColor(Color.WHITE);
    btn1.setFont(btnFont);
    btn1.drawString(fullBridgeName, mesFillRectStrX, mesFillRectBriStrY);

    Graphics2D btn2 = image.createGraphics();
    btn2.setColor(new Color(r, g, b));
    btn2.fillRect(mesFillRectX, mesFillRectYMem, mesFillRectWidth, mesFillRectHeight);
    btn2.setColor(Color.WHITE);
    btn2.setFont(btnFont);
    btn2.drawString(fullMemberName, mesFillRectStrX, mesFillRectMemStrY);

    Graphics2D btn3 = image.createGraphics();
    btn3.setColor(new Color(r, g, b));
    btn3.fillRect(mesFillRectX, mesFillRectYCharge, mesFillRectWidth, mesFillRectHeight);
    btn3.setColor(Color.WHITE);
    btn3.setFont(btnFont);
    btn3.drawString(fullChargeName, mesFillRectStrX, mesFillRectChargeStrY);

    Graphics2D btn4 = image.createGraphics();
    btn4.setColor(new Color(r, g, b));
    btn4.fillRect(mesFillRectX, mesFillRectYTel, mesFillRectWidth, mesFillRectHeight);
    btn4.setColor(Color.WHITE);
    btn4.setFont(btnFont);
    btn4.drawString(fullTelNumber, mesFillRectStrX, mesFillRectTelStrY);

    if(StringUtils.isNotEmpty(remark)){
      List<String> remarkArr = new ArrayList<>();

      int lineNo = 1;
      if(length>22){
        fullMark = fullMark + remark.substring(0,21);
        String resStr = remark.substring(21);
        int resLength = resStr.length();
        int a = resLength / 26;
        int c = resLength % 26;
        remarkArr.add(fullMark);

        if(c != 0){
          lineNo = lineNo + a + 1;
          int j;
          for(j = 0; j< lineNo -2; j++){
            remarkArr.add(resStr.substring(j*26,(j+1)*26));
          }
          remarkArr.add(resStr.substring(j*26));
        }else {
          lineNo = lineNo + a;
          int j;
          for(j = 0; j< lineNo -2; j++){
            remarkArr.add(resStr.substring(j*26,(j+1)*26));
          }
        }
      }else {
        fullMark = fullMark + remark;
        remarkArr.add(fullMark);
      }

      Graphics2D btn5 = image.createGraphics();
      btn5.setColor(new Color(r, g, b));
      btn5.fillRect(140, mesFillRectYMark, 800, 60*(lineNo));
      btn5.setColor(Color.WHITE);
      btn5.setFont(btnFont);

      for(int i = 0;i< lineNo;i++){
        btn5.drawString(remarkArr.get(i), 140, mesFillRectMarkStrY+60*(i));
      }
    }
    createImage(imgurl);
  }

  /**
   * 生成图片
   *
   * @param fileLocation 图片路径
   */
  private static void createImage(String fileLocation) {
    BufferedOutputStream bos = null;
    if (image != null) {
      try {
        FileOutputStream fos = new FileOutputStream(fileLocation);
        bos = new BufferedOutputStream(fos);

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
        encoder.encode(image);
        bos.close();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (bos != null) {//关闭输出流
          try {
            bos.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

}
