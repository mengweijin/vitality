package com.github.mengweijin.system.controller;

import com.github.mengweijin.system.dto.RoleDTO;
import com.github.mengweijin.system.entity.MenuDO;
import com.github.mengweijin.system.entity.RoleDO;
import com.github.mengweijin.system.service.MenuService;
import com.github.mengweijin.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/getUserPermission/{userId}")
    public List<String> getUserPermission(@PathVariable("userId") Long userId) {
        List<MenuDO> menuList = menuService.getMenuByLoginUser(userId);
        return menuList.stream().map(MenuDO::getPermission).toList();
    }

    @GetMapping("/getUserRole/{userId}")
    public List<String> getUserRole(@PathVariable("userId") Long userId) {
        List<RoleDTO> roleList = roleService.getByUserId(userId);
        return roleList.stream().map(RoleDO::getCode).toList();
    }

}
