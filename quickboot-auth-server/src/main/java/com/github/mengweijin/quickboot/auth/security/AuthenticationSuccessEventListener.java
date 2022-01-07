package com.github.mengweijin.quickboot.auth.security;

import com.github.mengweijin.quickboot.auth.utils.SecurityUtils;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.entity.User;
import com.github.mengweijin.quickboot.auth.service.UserService;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 登录成功监听
 *
 * @author Meng Wei Jin
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginLogTask loginLogTask;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        WebAuthenticationDetails auth = (WebAuthenticationDetails)event.getAuthentication().getDetails();

        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // 从缓存中移除当前用户的登录失败次数记录
        Cache loginFailedCache = cacheManager.getCache(SecurityUtils.LOGIN_FAILED_CACHE);
        assert loginFailedCache != null;
        loginFailedCache.evictIfPresent(username);

        User user = userService.getByUsername(username);

        // 保存用户信息到session
        ServletUtils.getSession().setAttribute(ServletUtils.SESSION_USER, user);

        //开启异步任务，写入登录日志
        loginLogTask.addSuccessLoginLog(ServletUtils.getRequest(), username, auth.getRemoteAddress());
    }
}