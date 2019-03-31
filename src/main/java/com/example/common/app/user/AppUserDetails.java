package com.example.common.app.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author sen
 * @Date: 2019/3/17 13:27
 * @Description:
 */
public class AppUserDetails implements UserDetails, Serializable {
    private String userId;
    private String userName;
    private String password;
    private Object userDetails;
    private Boolean locked;
    private Boolean enabled;
    private Boolean expired;

    public AppUserDetails(){}

    public AppUserDetails(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public AppUserDetails(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public AppUserDetails(String userId, String userName, String password, Object userDetails) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userDetails = userDetails;
    }

    public AppUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
