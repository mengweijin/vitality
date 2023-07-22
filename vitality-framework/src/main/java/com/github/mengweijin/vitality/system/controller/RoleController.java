package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.RoleDTO;
import com.github.mengweijin.vitality.system.entity.RoleDO;
import com.github.mengweijin.vitality.system.service.RoleService;
import org.dromara.hutool.core.collection.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 角色管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@RestController
@RequestMapping("/vtl-role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @SaCheckPermission("system:role:add")
    @PostMapping
    public R add(RoleDO roleDO) {
        boolean bool = roleService.save(roleDO);
        return R.bool(bool);
    }

    @SaCheckPermission("system:role:edit")
    @PutMapping
    public R edit(RoleDO roleDO) {
        boolean bool = roleService.updateById(roleDO);
        return R.bool(bool);
    }

    @SaCheckPermission("system:role:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = roleService.removeById(id);
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public RoleDO getById(@PathVariable("id") Long id) {
        return roleService.getById(id);
    }

    @SaCheckPermission("system:role:detail")
    @GetMapping("/detail/{id}")
    public RoleDTO detailById(@PathVariable("id") Long id) {
        return roleService.detailById(id);
    }

    @SaCheckPermission("system:role:list")
    @GetMapping("/page")
    public IPage<RoleDTO> page(Page<RoleDTO> page, RoleDTO dto) {
        return roleService.page(page, dto);
    }

    @GetMapping("/list")
    public List<RoleDO> list() {
        return roleService.lambdaQuery().eq(RoleDO::getDisabled, 0).list();
    }

    @SaCheckPermission("system:role:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = roleService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @SaCheckPermission("system:role:assignUser")
    @PostMapping("/addUser/{id}")
    public R addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        roleService.addUsers(id, ListUtil.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        roleService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }

    @SaCheckPermission("system:role:authorization")
    @PostMapping("/setMenu/{id}")
    public R setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        roleService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }
}
