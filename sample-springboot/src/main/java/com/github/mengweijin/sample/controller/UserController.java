package com.github.mengweijin.sample.controller;

import com.github.mengweijin.cache.expired.CacheExpired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.temporal.ChronoUnit;

/**
 * @author mengweijin
 * @date 2021/12/18
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @CacheExpired(expire = 10, chronoUnit = ChronoUnit.SECONDS)
    @Cacheable(cacheNames = "user")
    @GetMapping("/hello")
    public String hello(){
        log.info("Entered hello method.");
        return "Hello";
    }
}
