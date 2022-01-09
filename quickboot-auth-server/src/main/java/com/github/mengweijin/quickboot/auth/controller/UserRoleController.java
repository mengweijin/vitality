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
import com.github.mengweijin.quickboot.auth.data.entity.UserRole;
import com.github.mengweijin.quickboot.auth.data.service.UserRoleService;
import java.io.Serializable;

/**
 * <p>
 * 角色和用户关联表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user-role")
public class UserRoleController  {

    /**
     * <p>
     * UserRoleService
     * </p>
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * <p>
     * Get UserRole by id
     * </p>
     * @param id id
     * @return UserRole
     */
    @GetMapping("/{id}")
    public UserRole getById(@PathVariable("id") Serializable id) {
        return userRoleService.getById(id);
    }

    /**
     * <p>
     * Add UserRole
     * </p>
     * @param userRole userRole
     */
    @PostMapping
    public void add(@Valid @RequestBody UserRole userRole) {
        userRoleService.save(userRole);
    }

    /**
     * <p>
     * Update UserRole
     * </p>
     * @param userRole userRole
     */
    @PutMapping
    public void update(@Valid @RequestBody UserRole userRole) {
        userRoleService.updateById(userRole);
    }

    /**
     * <p>
     * Delete UserRole by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        userRoleService.removeById(id);
    }

}

