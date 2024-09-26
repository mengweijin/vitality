package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.domain.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    /**
     * 设置登录用户缓存
     */
    public static void setLoginUser(LoginUser loginUser) {
        StpUtil.getTokenSession().set(UserConst.SESSION_LOGIN_USER, loginUser);
    }

    /**
     * 获取登录用户
     */
    public static LoginUser getLoginUser() {
        return getLoginUser(StpUtil.getTokenSession());
    }

    public static LoginUser getLoginUserQuietly() {
        try {
            return getLoginUser();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getLoginUserIdQuietly() {
        return Optional.ofNullable(getLoginUserQuietly()).map(LoginUser::getUserId).orElse(null);
    }

    /**
     * 获取登录用户
     */
    public static LoginUser getLoginUser(String token) {
        return getLoginUser(StpUtil.getTokenSessionByToken(token));
    }

    private static LoginUser getLoginUser(SaSession session){
        if (session == null) {
            return null;
        }
        return (LoginUser) session.get(UserConst.SESSION_LOGIN_USER);
    }

    public static List<String> getPermissionList() {
        return StpUtil.getPermissionList();
    }

    public static List<String> getRoleList() {
        return StpUtil.getRoleList();
    }

    /**
     * 是否为管理员
     *
     * @return boolean
     */
    public static boolean isAdmin() {
        LoginUser loginUser = getLoginUser();
        return UserConst.ADMIN_USER_ID == loginUser.getUserId() && UserConst.ADMIN_USERNAME.equals(loginUser.getUsername());
    }



}
