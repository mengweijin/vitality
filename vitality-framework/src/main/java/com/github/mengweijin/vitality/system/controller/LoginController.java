package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.ratelimit.ERateLimitStrategy;
import com.github.mengweijin.vitality.framework.ratelimit.RateLimit;
import com.github.mengweijin.vitality.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.vitality.system.domain.LoginUser;
import com.github.mengweijin.vitality.system.domain.bo.LoginBO;
import com.github.mengweijin.vitality.system.domain.pure.PureLoginUser;
import com.github.mengweijin.vitality.system.service.LoginService;
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

    @SaIgnore
    @RepeatSubmit
    @RateLimit(duration = 5, max = 1, strategy = ERateLimitStrategy.IP)
    @PostMapping("/login")
    public PureLoginUser login(@Valid @RequestBody LoginBO loginBO) {
        LoginUser loginUser = loginService.login(loginBO);
        return PureLoginUser.success(loginUser);
    }

    @Deprecated
    @SaIgnore
    @RepeatSubmit
    @PostMapping("/login-token")
    public R<LoginUser> loginR(@Valid @RequestBody LoginBO loginBO) {
        LoginUser loginUser = loginService.login(loginBO);
        return R.success(loginUser);
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        StpUtil.logout();
        return R.success();
    }


    @SaIgnore
    @GetMapping("/captcha")
    @Deprecated
    @RateLimit(duration = 5, max = 1, strategy = ERateLimitStrategy.IP)
    public String createCaptcha() {
        return loginService.createCaptcha();
    }

}