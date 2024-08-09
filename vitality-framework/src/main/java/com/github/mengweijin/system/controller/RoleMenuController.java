package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.RoleMenu;
import com.github.mengweijin.system.service.RoleMenuService;
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
 *  RoleMenu Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/role-menu")
public class RoleMenuController {

    private RoleMenuService roleMenuService;

    /**
     * <p>
     * Get RoleMenu page by RoleMenu
     * </p>
     * @param page page
     * @param roleMenu {@link RoleMenu}
     * @return Page<RoleMenu>
     */
    @SaCheckPermission("system:roleMenu:query")
    @GetMapping("/page")
    public IPage<RoleMenu> page(Page<RoleMenu> page, RoleMenu roleMenu) {
        return roleMenuService.page(page, roleMenu);
    }

    /**
     * <p>
     * Get RoleMenu list by RoleMenu
     * </p>
     * @param roleMenu {@link RoleMenu}
     * @return List<RoleMenu>
     */
    @SaCheckPermission("system:roleMenu:query")
    @GetMapping("/list")
    public List<RoleMenu> list(RoleMenu roleMenu) {
        return roleMenuService.list(new QueryWrapper<>(roleMenu));
    }

    /**
     * <p>
     * Get RoleMenu by id
     * </p>
     * @param id id
     * @return RoleMenu
     */
    @SaCheckPermission("system:roleMenu:query")
    @GetMapping("/{id}")
    public RoleMenu getById(@PathVariable("id") Long id) {
        return roleMenuService.getById(id);
    }

    /**
     * <p>
     * Add RoleMenu
     * </p>
     * @param roleMenu {@link RoleMenu}
     */
    @SaCheckPermission("system:roleMenu:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody RoleMenu roleMenu) {
        boolean bool = roleMenuService.save(roleMenu);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update RoleMenu
     * </p>
     * @param roleMenu {@link RoleMenu}
     */
    @SaCheckPermission("system:roleMenu:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody RoleMenu roleMenu) {
        boolean bool = roleMenuService.updateById(roleMenu);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete RoleMenu by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:roleMenu:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = roleMenuService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

