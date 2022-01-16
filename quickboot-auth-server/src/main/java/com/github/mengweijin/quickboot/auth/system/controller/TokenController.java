package com.github.mengweijin.quickboot.auth.system.controller;

import com.github.mengweijin.quickboot.auth.domain.LoginUser;
import com.github.mengweijin.quickboot.auth.system.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 获取 token：http://localhost:8080/login?username={username}&password={password}
 * 验证 token：http://localhost:8080/token/verify?token={token}
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * <p>
     * token verify.
     * </p>
     * @param token token
     * @return true/false
     */
    @GetMapping("/verify")
    public boolean verify(@RequestParam("token") @NotBlank String token) {
        final LoginUser loginUser = tokenService.getLoginUser(token);
        if(loginUser != null) {
            // 自动续约 token
            tokenService.expireRefresh(loginUser.getUsername(), loginUser.getUuid());
            return true;
        }
        return false;
    }

}

