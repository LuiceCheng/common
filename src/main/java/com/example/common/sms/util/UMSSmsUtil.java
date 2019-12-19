package com.example.common.sms.util;

import com.example.common.utils.DateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhangwen
 * @description 一信通平台短信服务工具类
 * @date 2018-11-26 11:35
 */
@Component
public class UMSSmsUtil {

    private static String url;

    private static String spCode;

    private static String loginName;

    private static String password;

    @Value("${ums.sms.url}")
    public void setUrl(String url) {
        UMSSmsUtil.url = url;
    }

    @Value("${ums.sms.spCode}")
    public void setSpCode(String spCode) {
        UMSSmsUtil.spCode = spCode;
    }

    @Value("${ums.sms.loginName}")
    public void setLoginName(String loginName) {
        UMSSmsUtil.loginName = loginName;
    }

    @Value("${ums.sms.password}")
    public void setPassword(String password) {
        UMSSmsUtil.password = password;
    }

    public static boolean sendMsg(String phone, String messageContent) {
        String serialNumber = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS"
                + RandomStringUtils.random(3, false, true));
        InputStream is = null;
        try{
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            List<NameValuePair> valuePairs = new LinkedList<>();
            valuePairs.add(new BasicNameValuePair("SpCode", spCode));
            valuePairs.add(new BasicNameValuePair("LoginName", loginName));
            valuePairs.add(new BasicNameValuePair("Password", password));
            valuePairs.add(new BasicNameValuePair("MessageContent", messageContent));
            valuePairs.add(new BasicNameValuePair("UserNumber", phone));
            valuePairs.add(new BasicNameValuePair("SerialNumber", serialNumber));
            valuePairs.add(new BasicNameValuePair("ScheduleTime", ""));
            valuePairs.add(new BasicNameValuePair("f", "1"));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, "gbk");
            entity.setContentType("application/x-www-form-urlencoded");
            post.setEntity(entity);

            HttpResponse httpResponse = httpclient.execute(post);
            is = httpResponse.getEntity().getContent();
            List<String> lines = IOUtils.readLines(is, "gbk");
            if (null == lines || lines.size() == 0) {
                return false;
            }
//            log.info("UMS SMS 手机号：{} 请求数据：{} 响应数据：{}", phone, messageContent, lines.get(0));
            if (lines.get(0).indexOf("result=0") < 0) {
                return false;
            }
        } catch (Exception e) {
//            log.error("UMS SMS 短信发送失败", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return true;
    }

}
