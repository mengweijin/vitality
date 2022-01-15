package com.github.mengweijin.quickboot.auth.client.controller;

import com.github.mengweijin.quickboot.auth.client.filter.ClientTokenVerifyFilter;
import com.github.mengweijin.quickboot.auth.client.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.nio.charset.StandardCharsets;

/**
 * @author mengweijin
 * @date 2022/1/15
 */
@RestController
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/login")
    public R login(@RequestParam String username, @RequestParam String password) {
        String url = UriComponentsBuilder.fromHttpUrl(ClientTokenVerifyFilter.AUTH_LOGIN_URL)
                .queryParam("username", username)
                .queryParam("password", password)
                .build().encode(StandardCharsets.UTF_8).toString();
        return restTemplate.getForObject(url, R.class);
    }
}
