package com.example.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 获取图片的base64编码
 */
public class ImageBaseUtil {

    /**
     * @param imgFile 图片
     * @return String
     * @Description:获取图片的base64编码
     */
    public static String getImageStr(String imgFile) throws Exception {

        try {
            InputStream in = null;
            byte[] data = null;
            if (imgFile.startsWith("http")) { //获取在线图片
                URL url = new URL(imgFile);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5 * 1000);
                in = conn.getInputStream();
            } else { //获取线下图片
                in = new FileInputStream(imgFile);
            }
            int c;
            ByteArrayOutputStream buff = new ByteArrayOutputStream();
            while ((c = in.read()) >= 0) {
                buff.write(c);
            }
            data = buff.toByteArray();
            buff.close();
            in.read(data);
            in.close();
            BASE64Encoder encoder = new BASE64Encoder();
            if (data != null && data.length > 0) {
                return ImageBaseUtil.clearHtmlCode(encoder.encode(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param htmlCode base64编码
     * @return String   处理后的base64编码
     * @Description:清除base64编码中的特殊符号
     */
    public static String clearHtmlCode(String htmlCode) {
		/*htmlCode = htmlCode.replaceAll("\t", "").replaceAll("\r\n", "").replaceAll("\n", "");
		htmlCode = htmlCode.replaceAll("<SCRIPT(.[^(</SCRIPT>)]*)</SCRIPT>", "");
		htmlCode = htmlCode.replaceAll("<script(.[^(</script>)]*)</script>", "");
		htmlCode = htmlCode.replaceAll("<(.[^>]*)>", "");
		htmlCode = htmlCode.replaceAll("&nbsp;", "");*/
        htmlCode = htmlCode.replaceAll("&", "&amp;");
        htmlCode = htmlCode.replaceAll("<", "&lt;");
        htmlCode = htmlCode.replaceAll(">", "&gt;");
        return htmlCode;
    }

    /**
     * @param htmlCode base64编码
     * @return String   处理后的base64编码
     * @Description:反转base64编码中的特殊符号
     */
    public static String setHtmlCode(String htmlCode) {
        htmlCode = htmlCode.replaceAll("&amp;", "&");
        htmlCode = htmlCode.replaceAll("&lt;", "<");
        htmlCode = htmlCode.replaceAll("&gt;", ">");
        return htmlCode;
    }

    public static String filter(String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        StringBuffer result = null;
        String filtered = null;
        for (int i = 0; i < value.length(); i++) {
            filtered = null;
            switch (value.charAt(i)) {
                case 60: // '<'
                    filtered = "&lt;";
                    break;
                case 62: // '>'
                    filtered = "&gt;";
                    break;
                case 38: // '&'
                    filtered = "&amp;";
                    break;
                case 34: // '"'
                    filtered = "&quot;";
                    break;
                case 39: // '\''
                    filtered = "'";
                    break;
            }
            if (result == null) {
                if (filtered != null) {
                    result = new StringBuffer(value.length() + 50);
                    if (i > 0) {
                        result.append(value.substring(0, i));
                    }
                    result.append(filtered);
                }
            } else if (filtered == null) {
                result.append(value.charAt(i));
            } else {
                result.append(filtered);
            }
        }
        return result != null ? result.toString() : value;
    }


    /**
     * 将一个base64转换成图片保存在path文件夹下,命名随机
     *
     * @param base64String 图片base64编码
     * @param path         是一个文件夹路径
     * @throws Exception
     */
    public static String savePictoServer(String base64String, String path) {
        BASE64Decoder decoder = new BASE64Decoder();
        //要把+在上传时变成的空格再改为+
        base64String = base64String.replaceAll(" ", "+");
        //去掉“data:image/png;base64,”后面才是base64编码，去掉之后才能解析
        base64String = base64String.replace("data:image/png;base64,", "");
        //在本地指定位置建立文件夹，path由控制台那边进行定义
        String realPath = path + "/" + "diseaseIma";
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = path + "\\" + "diseaseIma" + "\\" + UUID.randomUUID().toString() + ".png";
        try {
            byte[] buffer = decoder.decodeBuffer(base64String);
            OutputStream os = new FileOutputStream(fileName);
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] < 0) {//调整异常数据
                    buffer[i] += 256;
                }
            }
            os.write(buffer);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}