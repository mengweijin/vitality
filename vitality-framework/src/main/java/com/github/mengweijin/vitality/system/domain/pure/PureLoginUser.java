package com.github.mengweijin.vitality.system.domain.pure;

import com.github.mengweijin.vitality.system.domain.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

/**
 * <p>
 * Pure Login User
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PureLoginUser implements Serializable {

    private Boolean success;

    private PureLoginUserData data;

    public static PureLoginUser success(LoginUser loginUser) {
        PureLoginUserData data = new PureLoginUserData();
        data.setAvatar(loginUser.getProfile());
        data.setUsername(loginUser.getUsername());
        data.setNickname(loginUser.getNickname());
        data.setRoles(loginUser.getRoles());
        data.setPermissions(loginUser.getPermissions());
        data.setAccessToken(loginUser.getToken());
        data.setRefreshToken(null);
        data.setExpires(null);
        return new PureLoginUser(true, data);
    }

    @Data
    public static class PureLoginUserData {
        /**
         * 用户图像
         */
        private String avatar;

        /**
         * 用户登录名（字母数字下划线）
         */
        private String username;

        /**
         * 用户昵称
         */
        private String nickname;

        /**
         * 用户角色
         */
        private Set<String> roles;

        /**
         * 用户权限
         */
        private Set<String> permissions;

        /**
         * 用户 token
         */
        private String accessToken;

        @Deprecated
        private String refreshToken;

        @Deprecated
        private String expires;

    }
}
