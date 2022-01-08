package com.github.mengweijin.quickboot.auth.controller;

import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.mengweijin.quickboot.auth.entity.LoginLog;
import com.github.mengweijin.quickboot.auth.service.LoginLogService;
import java.io.Serializable;

/**
 * <p>
 * 登录日志表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/login-log")
public class LoginLogController  {

    /**
     * <p>
     * LoginLogService
     * </p>
     */
    @Autowired
    private LoginLogService loginLogService;

    /**
     * <p>
     * Get LoginLog by id
     * </p>
     * @param id id
     * @return LoginLog
     */
    @GetMapping("/{id}")
    public LoginLog getById(@PathVariable("id") Serializable id) {
        return loginLogService.getById(id);
    }

    /**
     * <p>
     * Add LoginLog
     * </p>
     * @param loginLog loginLog
     */
    @PostMapping
    public void add(@Valid @RequestBody LoginLog loginLog) {
        loginLogService.save(loginLog);
    }

    /**
     * <p>
     * Update LoginLog
     * </p>
     * @param loginLog loginLog
     */
    @PutMapping
    public void update(@Valid @RequestBody LoginLog loginLog) {
        loginLogService.updateById(loginLog);
    }

    /**
     * <p>
     * Delete LoginLog by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        loginLogService.removeById(id);
    }

}

