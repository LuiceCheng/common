package com.example.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/11/28 11:18
 */
@ApiModel("用户认证")
public class AuthRequestUser {

    @ApiModelProperty(value = "用户名", required = true)
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
