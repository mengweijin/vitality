package com.github.mengweijin.quickboot.auth.security;

import cn.hutool.core.util.NumberUtil;
import com.github.mengweijin.quickboot.auth.async.LoginLogTask;
import com.github.mengweijin.quickboot.auth.data.entity.Auth;
import com.github.mengweijin.quickboot.auth.data.entity.User;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.security.handler.QuickBootAuthenticationFailureHandler;
import com.github.mengweijin.quickboot.auth.data.service.AuthService;
import com.github.mengweijin.quickboot.auth.data.service.UserService;
import com.github.mengweijin.quickboot.framework.redis.RedisCache;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Meng Wei Jin
 **/
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private LoginLogTask loginLogTask;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 获取用户登录失败次数
        Object loginFailedTimes = redisCache.getCacheObject(QuickBootAuthenticationFailureHandler.LOGIN_FAILED_TIMES_KEY + username);
        if(loginFailedTimes != null &&
            NumberUtil.parseInt(String.valueOf(loginFailedTimes)) > authProperties.getLogin().getMaxFailureTimes()) {
            // 已达到最大失败登录次数限制，账号已锁定  已达最大登录次数
            String message = "The maximum number of login attempts has been reached. The user has been locked, please try again " + authProperties.getLogin().getExpire() + " minutes later!";
            loginLogTask.addFailureLoginLog(ServletUtils.getRequest(), username, message);
            throw new LockedException(message);
        }

        User user = userService.getByUsername(username);
        if (user == null) {
            String message = "User was not found!";
            loginLogTask.addFailureLoginLog(ServletUtils.getRequest(), username, message);
            throw new UsernameNotFoundException(message);
        }

        List<Auth> authList = authService.selectAuthByUserId(user.getId());
        List<SimpleGrantedAuthority> authorityList = this.createAuthorities(authList);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
    }

    /**
     * 角色菜单权限字符串转化
     *
     * security 判断权限时是以ROLE_开头的权限标识，如 "ROLE_USER,ROLE_ADMIN"
     * SimpleGrantedAuthority("ROLE_system_user_add")用户新增的权限
     *
     * @param authList authList
     */
    private List<SimpleGrantedAuthority> createAuthorities(List<Auth> authList) {
        // 添加权限
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>(authList.size());
        for (Auth auth: authList) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + auth.getAuthKey()));
        }
        return simpleGrantedAuthorities;
    }
}
