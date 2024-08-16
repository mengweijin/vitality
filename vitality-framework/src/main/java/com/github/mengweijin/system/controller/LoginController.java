package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.system.domain.bo.LoginBody;
import com.github.mengweijin.system.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/captcha")
    public String createCaptcha() {
        return loginService.createCaptcha();
    }

    @SaIgnore
    @RepeatSubmit
    @PostMapping("/login")
    public R<Map<String, String>> login(@Valid @RequestBody LoginBody loginBody) {
        String token = loginService.login(loginBody);
        Map<String, String> map = new HashMap<>(1);
        map.put("token", token);
        return R.success(map);
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        StpUtil.logout();
        return R.success();
    }

}