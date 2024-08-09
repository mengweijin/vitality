package com.github.mengweijin.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.system.service.MenuService;
import com.github.mengweijin.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author mengweijin
 * @since 2023/6/22
 */
@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private MenuService menuService;

    private RoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return menuService.getMenuPermissionListByLoginUsername((String) loginId);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return roleService.getRoleCodeByUsername((String) loginId);
    }

}

