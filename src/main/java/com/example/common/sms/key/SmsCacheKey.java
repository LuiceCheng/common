package com.example.common.sms.key;

/**
 * @author zhangwen
 * @description 短信缓存key
 * @date 2018-10-22 15:11
 */
public class SmsCacheKey {

    /**
     * 短信验证码key
     * @return
     */
    public static String keyOfSmsVcode(String phone) {
        return "sms_vcode_" + phone;
    }

    /**
     * 验证码发送时间间隔key
     * @param phone
     * @return
     */
    public static String keyOfSmsVcodeSendInterval(String phone) {
        return "sms_vcode_send_interval" + phone;
    }

    /**
     * 短信验证通过颁发令牌key
     * @param phone
     * @return
     */
    public static String keyOfSmsVcodeToken(String phone) {
        return "sms_vcode_token_" + phone;
    }
}
