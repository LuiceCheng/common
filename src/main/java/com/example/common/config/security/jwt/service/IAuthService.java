package com.example.common.config.security.jwt.service;

import com.example.common.entity.Msg;
import com.example.common.model.JwtUser;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/29 9:09
 */
public interface IAuthService {

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    Msg<JwtUser> login(String userName, String password);

    /**
     * 退出登录
     * @param token
     */
    void logout(String token);

    /**
     * 刷新token
     * @param token
     * @return
     */
    Msg<JwtUser> refresh(String token);

    /**
     * 根据token获取用户
     * @param token
     * @return
     */
    JwtUser getUserByToken(String token);
}
