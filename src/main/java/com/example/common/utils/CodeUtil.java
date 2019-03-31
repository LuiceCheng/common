package com.example.common.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Author sen
 * @Date: 2019/2/24 17:50
 * @Description:
 */
public class CodeUtil {
    public static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER_CHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBER_CHAR = "0123456789";

    /**
     * 生成验证码
     *
     * @return
     */
    public static String generateCode() {
        Random r = new Random();
        Double d = r.nextDouble();
        String s = String.valueOf(d);
        s = s.substring(3, 3 + 6);
        return s;
    }

    /**
     * 获取指定长度的纯数字码
     *
     * @param len
     * @return
     */
    public static String getNumberStr(int len) {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        r.setSeed(new Date().getTime());
        StringBuffer buffer = new StringBuffer(NUMBER_CHAR);
        int range = buffer.length();
        for (int i = 0; i < len; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    /**
     * java随机生成数字和字母组合
     * @param len
     * @return
     */
    public static String getCharAndNumber(int len) {
        String value = "";
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            boolean charOrNum = random.nextInt(2) % 2 == 0 ? true : false;
            if (charOrNum) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                value += (char)(choice + random.nextInt(20));
            }else {
                value += String.valueOf(random.nextInt(10));
            }
        }
        return value;
    }

    public static String getUUID(int length){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0,length);
    }

    public static String getNonLineUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(random.nextInt(LETTER_CHAR.length())));
        }
        return sb.toString();
    }

    /**
     * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateLowerString(int length) {
        return generateMixString(length).toLowerCase();
    }
    /**
     * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateUpperString(int length) {
        return generateMixString(length).toUpperCase();
    }

    /**
     * 生成一个定长的纯0字符串
     *
     * @param length
     *            字符串长度
     * @return 纯0字符串
     */
    public static String generateZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * 根据数字生成一个定长的字符串，长度不够前面补0
     *
     * @param num
     *            数字
     * @param fixdlenth
     *            字符串长度
     * @return 定长的字符串
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(generateZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
                    + "的字符串发生异常！");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * 每次生成的len位数都不相同
     *
     * @param param
     * @return 定长的数字
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * 生成时间字符串
     * @param length 附带随机字（字符+数字）符串长度
     * @return String
     */
    public static String getTimeStringPlus(int length){
        //当前时间戳
        String timeString = String.valueOf(System.currentTimeMillis());
        //加入2位随机数
        if(length > 0){
            timeString = timeString.concat(getCharAndNumber(length));
        }
        return timeString;
    }
}
