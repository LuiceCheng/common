package com.example.common.app;

import com.example.common.app.user.AppUserDetails;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author sen
 * @Date: 2019/3/17 13:41
 * @Description:
 */
public class ContextUtil {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        ContextUtil.context = context;
    }

    public static AppUserDetails getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        AppUserDetails user;
        if(principal instanceof AppUserDetails){
            user = (AppUserDetails) principal;
        } else {
            user = new AppUserDetails();
            user.setUserId("");
            if(principal instanceof String){
                user.setUserId((String) principal);
            }
        }
        return user;
    }
}
