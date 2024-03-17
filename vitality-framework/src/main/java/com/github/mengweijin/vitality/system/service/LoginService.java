package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.cache.CacheConst;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.system.entity.UserDO;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.CircleCaptcha;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.CacheManager;

/**
 * 登录 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
public class LoginService {

    @Autowired
    private UserService userService;
    @Autowired
    private LogLoginService logLoginService;
    @Autowired
    private CacheManager cacheManager;

    public UserDO login(String username, String password, boolean rememberMe, String captchaCode) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = userAgent.getPlatform().getName();
        try {
            this.verifyCaptcha(captchaCode);

            UserDO user = userService.getByUsername(username);
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
            logLoginService.addLoginLogAsync(username, ELoginType.LOGIN, e.getMessage(), request);
            throw new LoginFailedException(e);
        }
    }

    public String getCaptcha() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.ofCircleCaptcha(200, 100, 4, 20);
        captcha.createCode();
        String sessionId = ServletUtils.getSession().getId();
        Cache<Object, Object> cache = cacheManager.getCache(CacheConst.NAME_1_MINUTES);
        cache.remove(sessionId);
        cache.put(sessionId, captcha);
        return captcha.getImageBase64Data();
    }

    public void verifyCaptcha(String captchaCode) {
        if(StrUtil.isNotBlank(captchaCode)) {
            Cache<Object, Object> cache = cacheManager.getCache(CacheConst.NAME_1_MINUTES);
            String sessionId = ServletUtils.getSession().getId();
            Object captchaObject = cache.get(sessionId);

            boolean verify = false;
            if(captchaObject != null) {
                ICaptcha captcha = (ICaptcha) captchaObject;
                verify = captcha.verify(captchaCode);
            }
            if(!verify) {
                throw new LoginFailedException("Captcha code verify failed! " + captchaCode);
            }
        }
    }

}
