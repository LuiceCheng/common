package com.example.common.sms.service;

/**
 * description: 短信服务 https://www.ums86.com
 *
 * @author:cxs
 * @date: 2019/12/19 17:42
 */
public interface ISmsService {
    /**
     * 发送短信验证码
     * @param phone
     */
    void sendSmsVcode(String phone);
}
