package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.entity.VtlRole;
import com.github.mengweijin.vitality.system.service.VtlMenuService;
import com.github.mengweijin.vitality.system.service.VtlRoleService;
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
    private VtlMenuService menuService;
    @Autowired
    private VtlRoleService roleService;

    @GetMapping("/getUserPermission/{userId}")
    public List<String> getUserPermission(@PathVariable("userId") Long userId) {
        List<VtlMenu> menuList = menuService.getMenuByLoginUser(userId);
        return menuList.stream().map(VtlMenu::getPermission).toList();
    }

    @GetMapping("/getUserRole/{userId}")
    public List<String> getUserRole(@PathVariable("userId") Long userId) {
        List<VtlRoleDTO> roleList = roleService.getByUserId(userId);
        return roleList.stream().map(VtlRole::getCode).toList();
    }

}
