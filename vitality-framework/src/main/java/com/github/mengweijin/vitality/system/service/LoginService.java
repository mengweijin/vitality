package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.cache.CacheFactory;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.domain.LoginUser;
import com.github.mengweijin.vitality.system.domain.bo.LoginBO;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.dromara.hutool.http.server.servlet.ServletUtil;
import org.dromara.hutool.http.useragent.Platform;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.swing.captcha.AbstractCaptcha;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import java.time.LocalDateTime;
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

    private ConfigService configService;

    public LoginUser login(LoginBO loginBO) {
        HttpServletRequest request = ServletUtils.getRequest();
        try {
            if(configService.getCaptchaEnabled()) {
                boolean validate = this.checkCaptcha(request, loginBO.getCaptcha());
                if(!validate) {
                    throw new LoginFailedException("The captcha code was invalid!");
                }
            }

            UserAgent userAgent = ServletUtils.getUserAgent(request);
            String platformName = Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(Platform::getName).orElse(null);

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
        loginUser.setLoginTime(LocalDateTime.now());
        return loginUser;
    }

    public String getCaptcha() {
        String ip = ServletUtil.getClientIP(ServletUtils.getRequest());
        Cache<String, AbstractCaptcha> captchaCache = CacheFactory.getCaptchaCache();

        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        AbstractCaptcha captcha = CaptchaUtil.ofLineCaptcha(200, 60, 4, 200);
        // AbstractCaptcha captcha = CaptchaUtil.ofShearCaptcha(200, 60, 4, 5);
        captcha.createCode();
        // 放入缓存
        captchaCache.put(ip, captcha);
        return captcha.getImageBase64Data();
    }

    private boolean checkCaptcha(HttpServletRequest request, @NotBlank String captcha) {
        Cache<String, AbstractCaptcha> captchaCache = CacheFactory.getCaptchaCache();
        String ip = ServletUtil.getClientIP(request);
        AbstractCaptcha abstractCaptcha = captchaCache.get(ip);
        return abstractCaptcha != null && abstractCaptcha.verify(captcha);
    }

}
