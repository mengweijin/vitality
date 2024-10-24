package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vitality.system.domain.entity.Role;
import com.github.mengweijin.vitality.system.enums.EYesNo;
import com.github.mengweijin.vitality.system.service.RoleService;
import com.github.mengweijin.vitality.system.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Role Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/role")
public class RoleController {

    private RoleService roleService;

    private UserRoleService userRoleService;

    /**
     * <p>
     * Get Role page by Role
     * </p>
     * @param page page
     * @param role {@link Role}
     * @return Page<Role>
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/page")
    public IPage<Role> page(Page<Role> page, Role role) {
        return roleService.page(page, role);
    }

    /**
     * <p>
     * Get Role list by Role
     * </p>
     * @param role {@link Role}
     * @return List<Role>
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/list")
    public List<Role> list(Role role) {
        return roleService.list(new LambdaQueryWrapper<>(role).eq(Role::getDisabled, EYesNo.N.getValue()));
    }

    /**
     * <p>
     * Get Role ids by id
     * </p>
     *
     * @param userId userId
     * @return Role
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/list-role-ids-by-user-id/{userId}")
    public Set<Long> getRoleIdsByUserId(@PathVariable("userId") Long userId) {
        return userRoleService.getRoleIdsByUserId(userId);
    }

    /**
     * <p>
     * Get Role by id
     * </p>
     * @param id id
     * @return Role
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/{id}")
    public Role getById(@PathVariable("id") Long id) {
        return roleService.getById(id);
    }

    /**
     * <p>
     * Add Role
     * </p>
     * @param role {@link Role}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:role:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody Role role) {
        boolean bool = roleService.save(role);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Role
     * </p>
     * @param role {@link Role}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:role:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody Role role) {
        boolean bool = roleService.updateById(role);
        return R.ajax(bool);
    }

    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:role:update")
    @PostMapping("/set-permission")
    public R<Void> setPermission(@Valid @RequestBody RolePermissionBO rolePermissionBO) {
        boolean bool = roleService.setMenuPermission(rolePermissionBO);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Role by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:role:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        return R.ajax(roleService.removeByIds(Arrays.asList(ids)));
    }

}

