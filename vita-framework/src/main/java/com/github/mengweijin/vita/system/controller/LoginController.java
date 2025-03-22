package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.ratelimit.ERateLimitStrategy;
import com.github.mengweijin.vita.framework.ratelimit.RateLimit;
import com.github.mengweijin.vita.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.vita.system.domain.LoginUser;
import com.github.mengweijin.vita.system.domain.bo.LoginBO;
import com.github.mengweijin.vita.system.service.ConfigService;
import com.github.mengweijin.vita.system.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Validated
@RestController
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    private ConfigService configService;

    @SaIgnore
    @RepeatSubmit(interval = 3000)
    @RateLimit(duration = 5, max = 1, strategy = ERateLimitStrategy.IP)
    @PostMapping("/login")
    public R<LoginUser> login(@Valid @RequestBody LoginBO loginBO) {
        LoginUser loginUser = loginService.login(loginBO);
        return R.success(loginUser);
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        StpUtil.logout();
        return R.success();
    }

    @SaIgnore
    @GetMapping("/captchaEnabled")
    public boolean getCaptchaEnabled() {
        return configService.getCaptchaEnabled();
    }

    @SaIgnore
    @GetMapping("/captcha")
    public String getCaptcha() {
        return loginService.getCaptcha();
    }

}