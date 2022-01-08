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
import com.github.mengweijin.quickboot.auth.data.entity.Role;
import com.github.mengweijin.quickboot.auth.data.service.RoleService;
import java.io.Serializable;

/**
 * <p>
 * 角色信息表 Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/role")
public class RoleController  {

    /**
     * <p>
     * RoleService
     * </p>
     */
    @Autowired
    private RoleService roleService;

    /**
     * <p>
     * Get Role by id
     * </p>
     * @param id id
     * @return Role
     */
    @GetMapping("/{id}")
    public Role getById(@PathVariable("id") Serializable id) {
        return roleService.getById(id);
    }

    /**
     * <p>
     * Add Role
     * </p>
     * @param role role
     */
    @PostMapping
    public void add(@Valid @RequestBody Role role) {
        roleService.save(role);
    }

    /**
     * <p>
     * Update Role
     * </p>
     * @param role role
     */
    @PutMapping
    public void update(@Valid @RequestBody Role role) {
        roleService.updateById(role);
    }

    /**
     * <p>
     * Delete Role by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        roleService.removeById(id);
    }

}

