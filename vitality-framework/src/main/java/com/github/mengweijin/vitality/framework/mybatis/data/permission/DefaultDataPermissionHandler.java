package com.github.mengweijin.vitality.framework.mybatis.data.permission;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/11/20
 */
public class DefaultDataPermissionHandler extends BaseDataPermissionHandler {
    @Override
    protected String getLoginUserId() {
        return null;
    }

    @Override
    protected boolean isAdminForLoginUserId() {
        return false;
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
