package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.Role;
import com.github.mengweijin.vitality.system.service.RoleService;
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

    /**
     * <p>
     * Get Role page by Role
     * </p>
     * @param page page
     * @param role {@link Role}
     * @return Page<Role>
     */
    @SaCheckPermission("system_role_query")
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
    @SaCheckPermission("system_role_query")
    @GetMapping("/list")
    public List<Role> list(Role role) {
        return roleService.list(new QueryWrapper<>(role));
    }

    /**
     * <p>
     * Get Role by id
     * </p>
     * @param id id
     * @return Role
     */
    @SaCheckPermission("system_role_query")
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
    @SaCheckPermission("system_role_create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody Role role) {
        boolean bool = roleService.save(role);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Role
     * </p>
     * @param role {@link Role}
     */
    @SaCheckPermission("system_role_update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody Role role) {
        boolean bool = roleService.updateById(role);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Role by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system_role_delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = roleService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

