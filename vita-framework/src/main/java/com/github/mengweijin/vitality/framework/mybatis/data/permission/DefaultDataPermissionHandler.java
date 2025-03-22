package com.github.mengweijin.vitality.framework.mybatis.data.permission;

import com.github.mengweijin.vitality.framework.satoken.LoginHelper;

import java.util.List;

/**
 * @author mengweijin
 * @since 2022/11/20
 */
public class DefaultDataPermissionHandler extends BaseDataPermissionHandler {
    @Override
    protected String getLoginUserId() {
        return String.valueOf(LoginHelper.getLoginUser().getUserId());
    }

    @Override
    protected boolean isAdmin() {
        return LoginHelper.isAdmin();
    }

    @Override
    protected List<String> getLoginUserDeptIdList() {
        return null;
    }

    @Override
    protected List<String> getLoginUserRoleIdList() {
        return null;
    }
}
