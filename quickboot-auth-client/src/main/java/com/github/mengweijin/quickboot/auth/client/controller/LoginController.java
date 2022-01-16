package com.github.mengweijin.quickboot.auth.client.controller;

import com.github.mengweijin.quickboot.auth.client.filter.AuthClientProperties;
import com.github.mengweijin.quickboot.auth.client.filter.ClientTokenVerifyFilter;
import com.github.mengweijin.quickboot.auth.client.utils.AuthClientConst;
import com.github.mengweijin.quickboot.auth.client.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
@Validated
@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthClientProperties authClientProperties;

    @PostMapping(ClientTokenVerifyFilter.LOGIN_URL)
    public R<?> login(@RequestParam @NotBlank String username,
                      @RequestParam @NotBlank String password,
                      HttpServletResponse response) {
        String url = UriComponentsBuilder.fromHttpUrl(authClientProperties.getLoginUrl())
                .queryParam("username", username)
                .queryParam("password", password)
                .build().encode(StandardCharsets.UTF_8).toString();
        R<Map<String, Object>> r = restTemplate.postForObject(url, null, R.class);

        Optional<R<Map<String, Object>>> optional = Optional.ofNullable(r);
        Boolean isSuccess = optional.map(R::getCode).map(code -> code == HttpStatus.OK.value()).orElse(false);
        if(isSuccess) {
            Object token = optional
                    .map(R::getData)
                    .map(map -> map.get("token"))
                    .orElseThrow(() -> new RuntimeException("An exception occurred while parsing the token from JSON!"));

            // token 写入 cookie
            Cookie cookie = new Cookie(AuthClientConst.COOKIE_NAME, String.valueOf(token));
            cookie.setMaxAge(30 * 60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return R.ok();
        } else {
            throw new RuntimeException("Login failed!");
        }
    }

}
