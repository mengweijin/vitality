package com.github.mengweijin.vita.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * Login User
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class LoginUser implements Serializable {

    private Long userId;

    /**
     * 用户登录名（字母数字下划线）
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户图像
     */
    private String avatar;

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
    private String token;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}
