package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.vitality.system.dto.RoleDTO;
import com.github.mengweijin.vitality.system.entity.MenuDO;
import com.github.mengweijin.vitality.system.entity.RoleDO;
import com.github.mengweijin.vitality.system.entity.UserDO;
import com.github.mengweijin.vitality.system.service.MenuService;
import com.github.mengweijin.vitality.system.service.RoleService;
import com.github.mengweijin.vitality.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author mengweijin
 * @date 2023/6/22
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        UserDO user = userService.getByUsername((String) loginId);
        List<MenuDO> menuList = menuService.getMenuByLoginUser(user.getId());
        return menuList.stream().map(MenuDO::getPermission).toList();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserDO user = userService.getByUsername((String) loginId);
        List<RoleDTO> roleList = roleService.getByUserId(user.getId());
        return roleList.stream().map(RoleDO::getCode).toList();
    }

}

