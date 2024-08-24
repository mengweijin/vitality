package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.cache.CacheManagerFactory;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.domain.LoginUser;
import com.github.mengweijin.vitality.system.domain.bo.LoginBO;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.Platform;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.dromara.hutool.swing.captcha.LineCaptcha;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
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

    private DeptService deptService;

    private RoleService roleService;

    private MenuService menuService;

    private LogLoginService logLoginService;

    public String login(LoginBO loginBO) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(Platform::getName).orElse(null);
        try {
            this.verifyCaptcha(loginBO.getCaptcha());

            User user = userService.getByUsername(loginBO.getUsername());
            if (user == null) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
            StpUtil.checkDisable(user.getId());

            if (!userService.checkPassword(loginBO.getPassword(), user.getPassword())) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            StpUtil.login(loginBO.getUsername(), new SaLoginModel().setIsLastingCookie(loginBO.isRememberMe()).setDevice(platformName));
            LoginHelper.setLoginUser(this.buildLoginUser(user));
            return StpUtil.getTokenValue();
        } catch (RuntimeException e) {
            logLoginService.addLoginLogAsync(loginBO.getUsername(), null, ELoginType.LOGIN, e.getMessage(), request);
            throw new LoginFailedException(e);
        }
    }

    public String createCaptcha() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha captcha = CaptchaUtil.ofLineCaptcha(200, 100, 4, 400);
        captcha.createCode();
        String sessionId = ServletUtils.getSession().getId();
        Cache<String, ICaptcha> cache = CacheManagerFactory.getCaptchaCache();
        cache.put(sessionId, captcha);
        return captcha.getImageBase64Data();
    }

    public void verifyCaptcha(String captchaCode) {
        if (StrUtil.isNotBlank(captchaCode)) {
            Cache<String, ICaptcha> cache = CacheManagerFactory.getCaptchaCache();
            String sessionId = ServletUtils.getSession().getId();
            ICaptcha captcha = cache.get(sessionId);
            if (captcha == null || !captcha.verify(captchaCode)) {
                throw new LoginFailedException("Captcha code verify failed! " + captchaCode);
            }
        }
    }

    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setToken(StpUtil.getTokenValue());
        return loginUser;
    }

}
