package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import com.github.mengweijin.vitality.system.service.VtlUserService;
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
    private VtlUserService userService;
    @Autowired
    private VtlUserMapper userMapper;

    @SaIgnore
    @PostMapping("/login")
    public R login(@NotBlank String username, @NotBlank String password, boolean rememberMe, String captcha) {
        VtlUser user = userService.getByUsername(username);
        if(!userService.checkPassword(password, user.getPassword())) {
            throw new ClientException("The username or password incorrect!");
        }
        StpUtil.login(user.getId(), rememberMe);
        return R.success();
    }

    @GetMapping("/login/userId")
    public String getLoginUserId() {
        return StpUtil.getLoginIdAsString();
    }

    @PostMapping("/logout")
    public R logout() {
        StpUtil.logout();
        return R.success();
    }

}
