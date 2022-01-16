package com.github.mengweijin.quickboot.sample.auth.client.controller;

import com.github.mengweijin.quickboot.sample.auth.client.entity.User;
import com.github.mengweijin.quickboot.sample.auth.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * <p>
 * 用户信息表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController  {

    /**
     * <p>
     * UserService
     * </p>
     */
    @Autowired
    private UserService userService;

    /**
     * <p>
     * Get User by id
     * </p>
     * @param id id
     * @return User
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Serializable id) {
        return userService.getById(id);
    }

    /**
     * <p>
     * Add User
     * </p>
     * @param user user
     */
    @PostMapping
    public void add(@Valid @RequestBody User user) {
        userService.save(user);
    }

    /**
     * <p>
     * Update User
     * </p>
     * @param user user
     */
    @PutMapping
    public void update(@Valid @RequestBody User user) {
        userService.updateById(user);
    }

    /**
     * <p>
     * Delete User by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        userService.removeById(id);
    }

}

