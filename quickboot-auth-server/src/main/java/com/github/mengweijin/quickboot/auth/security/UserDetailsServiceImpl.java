package com.github.mengweijin.quickboot.auth.security;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.quickboot.auth.entity.Auth;
import com.github.mengweijin.quickboot.auth.entity.User;
import com.github.mengweijin.quickboot.auth.entity.UserRole;
import com.github.mengweijin.quickboot.auth.mapper.AuthMapper;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.auth.service.AuthService;
import com.github.mengweijin.quickboot.auth.service.UserRoleService;
import com.github.mengweijin.quickboot.auth.service.UserService;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
    private UserRoleService userRoleService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private AuthenticationFailureListener authenticationFailureListener;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 缓存用户登录失败次数
        AtomicInteger retryCount = authenticationFailureListener.getLoginFailedCacheCount(username);

        // 账号已锁定
        if(retryCount.get() > authProperties.getLogin().getMaxFailureTimes()) {
            String message = "User locked, please try again " + authProperties.getLogin().getExpire() + " minutes later!";
            authenticationFailureListener.recordLoginFailed(username, message, null);
            throw new LockedException(message);
        }

        User user = userService.getByUsername(username);
        if (user == null) {
            String message = "User was not found!";
            authenticationFailureListener.recordLoginFailed(username, message, null);
            // spring security 默认不抛出UsernameNotFoundException的前台展示信息, 会转成BadCredentialsException，
            // 所以前台的错误信息提示总是会为：Bad credentials，所以这里使用自定义的异常类
            throw new QuickBootClientException(message);
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
    }

    /**
     * 角色菜单权限字符串转化
     *
     * security 判断权限时是以ROLE_开头的权限标识，如 "ROLE_USER,ROLE_ADMIN"
     * SimpleGrantedAuthority("ROLE_system_user_add")用户新增的权限
     *
     * @param user 用户
     */
    private List<SimpleGrantedAuthority> createAuthorities(User user) {
        List<Auth> authList = new ArrayList<>();

        LambdaQueryWrapper<UserRole> roleUserWrapper = new LambdaQueryWrapper<>();
        roleUserWrapper.eq(UserRole::getUserId, user.getId());
        List<UserRole> roleUserList = userRoleService.list(roleUserWrapper);

        // 当前用户没有分配角色
        if(CollectionUtil.isEmpty(roleUserList)) {
            // ignore. No need to add Authority.
        } else {
            // 筛选 roleId=1 的记录（roleId=1 表示角色为管理员，管理员拥有所有权限。）。
            boolean isAdmin = roleUserList.stream().anyMatch(roleUser -> roleUser.getRoleId() == 1L);
            if(isAdmin) {
                authList = authService.list();
            } else {
                Optional<List<Auth>> optional = authMapper.selectAuthByUserId(user.getId());
                if(optional.isPresent()) {
                    authList = optional.get();
                }
            }
        }

        // 添加权限
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>(authList.size());
        for (Auth auth: authList) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + auth.getAuthKey()));
        }
        return simpleGrantedAuthorities;
    }
}
