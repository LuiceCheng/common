package com.example.common.model;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/10/29 8:48
 */
public class JwtUser {
    private String token;
    private User user;

    public JwtUser() {
    }

    public JwtUser(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
