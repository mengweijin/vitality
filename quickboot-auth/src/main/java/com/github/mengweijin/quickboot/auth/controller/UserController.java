package com.github.mengweijin.quickboot.auth.controller;

import com.github.mengweijin.quickboot.auth.utils.SecurityUtils;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.github.mengweijin.quickboot.auth.data.entity.User;
import com.github.mengweijin.quickboot.auth.data.service.UserService;

import java.io.Serializable;

/**
 * <p>
 * 用户信息表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
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

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * <p>
     * Get User by username
     * </p>
     * @param username username
     * @return User
     */
    @GetMapping("/{username}")
    public User getByName(@PathVariable("username") String username) {
        return userService.getByUsername(username);
    }

    /**
     * <p>
     * Add User
     * </p>
     * @param user user
     */
    @RolesAllowed("user_insert")
    @PostMapping
    public void add(@Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @PostMapping("/changePassword")
    public void changePassword(@Valid @RequestBody @NotBlank String oldPassword,
                               @Valid @RequestBody @NotBlank String newPassword) {
        User user = userService.getByUsername(SecurityUtils.getUsername());
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateById(user);
        } else {
            throw new QuickBootClientException("The old password is not correct!");
        }
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
    @RolesAllowed("user_delete")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        userService.removeById(id);
    }

}

