package com.github.mengweijin.system.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * User VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {

    private Long id;

    /**
     * 用户登录名（字母数字下划线）
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 性别。关联数据字典：user_gender
     */
    private String gender;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 移动电话
     */
    private String mobile;

    /**
     * 是否禁用。[Y, N]
     */
    private String disabled;

    /**
     * 逻辑删除。[Y, N]
     */
    private String deleted;
}
