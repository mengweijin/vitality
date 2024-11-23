package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.monitor.service.LogLoginService;
import com.github.mengweijin.vitality.system.domain.LoginUser;
import com.github.mengweijin.vitality.system.domain.bo.LoginBO;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.dromara.hutool.http.useragent.Platform;
import org.dromara.hutool.http.useragent.UserAgent;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 登录 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
@AllArgsConstructor
public class LoginService {

    private UserService userService;

    private RoleService roleService;

    private MenuService menuService;

    private LogLoginService logLoginService;

    public LoginUser login(LoginBO loginBO) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(Platform::getName).orElse(null);
        try {
            // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
            StpUtil.checkDisable(loginBO.getUsername());

            User user = userService.getByUsername(loginBO.getUsername());
            if (user == null) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            if (!userService.checkPassword(loginBO.getPassword(), user.getPassword())) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            StpUtil.login(loginBO.getUsername(), new SaLoginModel().setIsLastingCookie(loginBO.isRememberMe()).setDevice(platformName));

            LoginUser loginUser = this.buildLoginUser(user);
            LoginHelper.setLoginUser(loginUser);
            return loginUser;
        } catch (RuntimeException e) {
            logLoginService.addLoginLogAsync(loginBO.getUsername(), ELoginType.LOGIN, e.getMessage(), request);
            throw new LoginFailedException(e);
        }
    }

    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(userService.getAvatarById(user.getId()));
        loginUser.setRoles(roleService.getRoleCodeByUsername(user.getUsername()));
        loginUser.setPermissions(menuService.getMenuPermissionListByLoginUsername(user.getUsername()));
        loginUser.setToken(StpUtil.getTokenValue());
        return loginUser;
    }

}
