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
import com.github.mengweijin.quickboot.auth.data.entity.RoleAuth;
import com.github.mengweijin.quickboot.auth.data.service.RoleAuthService;
import java.io.Serializable;

/**
 * <p>
 * 角色和权限关联表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/role-auth")
public class RoleAuthController  {

    /**
     * <p>
     * RoleAuthService
     * </p>
     */
    @Autowired
    private RoleAuthService roleAuthService;

    /**
     * <p>
     * Get RoleAuth by id
     * </p>
     * @param id id
     * @return RoleAuth
     */
    @GetMapping("/{id}")
    public RoleAuth getById(@PathVariable("id") Serializable id) {
        return roleAuthService.getById(id);
    }

    /**
     * <p>
     * Add RoleAuth
     * </p>
     * @param roleAuth roleAuth
     */
    @PostMapping
    public void add(@Valid @RequestBody RoleAuth roleAuth) {
        roleAuthService.save(roleAuth);
    }

    /**
     * <p>
     * Update RoleAuth
     * </p>
     * @param roleAuth roleAuth
     */
    @PutMapping
    public void update(@Valid @RequestBody RoleAuth roleAuth) {
        roleAuthService.updateById(roleAuth);
    }

    /**
     * <p>
     * Delete RoleAuth by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        roleAuthService.removeById(id);
    }

}

