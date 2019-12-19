package com.example.common.services;

import com.example.common.model.User;

public interface IUserService extends IBaseService<User>{

    /**
     * 获取短信认证token
     * @param phone 电话号码
     * @param vcode 短信验证码
     * @return
     */
    String getSmsVcodeToken(String phone, String vcode);

    /**
     * 修改密码
     * @param vcodeToken 短信验证码令牌
     * @param phone 原密码
     * @param newPwd 新密码
     */
    void modifyPwdWidthVcodeToken(String vcodeToken, String phone,String newPwd);
}
