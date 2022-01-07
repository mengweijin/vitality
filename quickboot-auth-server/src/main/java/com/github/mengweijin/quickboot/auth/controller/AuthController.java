package com.github.mengweijin.quickboot.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.github.mengweijin.quickboot.auth.entity.Auth;
import com.github.mengweijin.quickboot.auth.service.AuthService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;

/**
 * <p>
 * 权限表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController  {

    /**
     * <p>
     * AuthService
     * </p>
     */
    @Autowired
    private AuthService authService;

    /**
     * <p>
     * Get Auth by id
     * </p>
     * @param id id
     * @return Auth
     */
    @GetMapping("/{id}")
    public Auth getById(@PathVariable("id") Serializable id) {
        return authService.getById(id);
    }

    /**
     * <p>
     * Add Auth
     * </p>
     * @param auth auth
     */
    @PostMapping
    public void add(@Valid @RequestBody Auth auth) {
        authService.save(auth);
    }

    /**
     * <p>
     * Update Auth
     * </p>
     * @param auth auth
     */
    @PutMapping
    public void update(@Valid @RequestBody Auth auth) {
        authService.updateById(auth);
    }

    /**
     * <p>
     * Delete Auth by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        authService.removeById(id);
    }

}

