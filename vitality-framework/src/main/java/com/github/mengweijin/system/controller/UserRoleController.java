package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.UserRole;
import com.github.mengweijin.system.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  UserRole Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/user-role")
public class UserRoleController {

    private UserRoleService userRoleService;

    /**
     * <p>
     * Get UserRole page by UserRole
     * </p>
     * @param page page
     * @param userRole {@link UserRole}
     * @return Page<UserRole>
     */
    @SaCheckPermission("system:userRole:query")
    @GetMapping("/page")
    public IPage<UserRole> page(Page<UserRole> page, UserRole userRole) {
        return userRoleService.page(page, userRole);
    }

    /**
     * <p>
     * Get UserRole list by UserRole
     * </p>
     * @param userRole {@link UserRole}
     * @return List<UserRole>
     */
    @SaCheckPermission("system:userRole:query")
    @GetMapping("/list")
    public List<UserRole> list(UserRole userRole) {
        return userRoleService.list(new QueryWrapper<>(userRole));
    }

    /**
     * <p>
     * Get UserRole by id
     * </p>
     * @param id id
     * @return UserRole
     */
    @SaCheckPermission("system:userRole:query")
    @GetMapping("/{id}")
    public UserRole getById(@PathVariable("id") Long id) {
        return userRoleService.getById(id);
    }

    /**
     * <p>
     * Add UserRole
     * </p>
     * @param userRole {@link UserRole}
     */
    @SaCheckPermission("system:userRole:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody UserRole userRole) {
        boolean bool = userRoleService.save(userRole);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update UserRole
     * </p>
     * @param userRole {@link UserRole}
     */
    @SaCheckPermission("system:userRole:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody UserRole userRole) {
        boolean bool = userRoleService.updateById(userRole);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete UserRole by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:userRole:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = userRoleService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

