package com.github.mengweijin.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * User session VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
public class UserSessionVO implements Serializable {

    private Long id;

    /**
     * 用户登录名（字母数字下划线）
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

}
