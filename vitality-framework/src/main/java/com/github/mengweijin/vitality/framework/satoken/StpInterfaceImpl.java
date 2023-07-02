package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.vitality.system.service.VtlDeptService;
import com.github.mengweijin.vitality.system.service.VtlMenuService;
import com.github.mengweijin.vitality.system.service.VtlPostService;
import com.github.mengweijin.vitality.system.service.VtlRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author mengweijin
 * @date 2023/6/22
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private VtlMenuService menuService;
    @Autowired
    private VtlRoleService roleService;
    @Autowired
    private VtlDeptService deptService;
    @Autowired
    private VtlPostService postService;
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<>();
        list.add("101");
        list.add("user.add");
        list.add("user.update");
        list.add("user.get");
        // list.add("user.delete");
        list.add("art.*");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }

}

