package com.github.mengweijin.vitality.system.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * Login User
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
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
     * 用户 token
     */
    private String token;

}
