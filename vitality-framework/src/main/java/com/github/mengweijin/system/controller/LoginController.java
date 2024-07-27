package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.entity.UserDO;
import com.github.mengweijin.system.service.LoginService;
import com.github.mengweijin.system.service.UserService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @SaIgnore
    @PostMapping("/login")
    public R login(@NotBlank String username, @NotBlank String password, boolean rememberMe, String captcha) {
        UserDO user = loginService.login(username, password, rememberMe, captcha);

        UserDO userDO = new UserDO();
        userDO.setUsername(user.getUsername());
        userDO.setNickname(user.getNickname());
        userDO.setGender(user.getGender());
        userDO.setId(user.getId());
        return R.success(userDO);
    }

    @GetMapping("/login/userId")
    public Long getLoginUserId() {
        return userService.getSessionUser().getId();
    }

    @PostMapping("/logout")
    public R logout() {
        StpUtil.logout();
        return R.success();
    }

    @SaIgnore
    @GetMapping("/captcha")
    public String getCaptcha() {
        return loginService.getCaptcha();
    }

}
