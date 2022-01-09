package com.github.mengweijin.quickboot.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Meng Wei Jin
 **/
public class SecurityUtils {

    /**
     * 获取当前用户身份认证
     * @return Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     * @return UserDetails
     */
    public static UserDetails getPrincipal(){
        return (UserDetails) getAuthentication().getPrincipal();
    }

    /**
     * 获取当前用户登录名
     * @return username
     */
    public static String getUsername(){
        return getPrincipal().getUsername();
    }
}
