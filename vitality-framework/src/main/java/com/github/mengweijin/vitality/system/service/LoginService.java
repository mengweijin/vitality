package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.http.useragent.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class LoginService {

    @Autowired
    private VtlUserService userService;
    @Autowired
    private VtlLogLoginService vtlLogLoginService;

    public VtlUser login(String username, String password, boolean rememberMe, String captcha) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = userAgent.getPlatform().getName();
        try {
            VtlUser user = userService.getByUsername(username);
            if(user == null) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
            StpUtil.checkDisable(user.getId());

            if(!userService.checkPassword(password, user.getPassword())) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            StpUtil.login(username, new SaLoginModel().setIsLastingCookie(rememberMe).setDevice(platformName));

            userService.setSessionUser(user);
            return user;
        } catch (RuntimeException e) {
            vtlLogLoginService.addLoginLogAsync(username, ELoginType.LOGIN, e.getMessage(), request);
            throw new LoginFailedException(e);
        }
    }
}
