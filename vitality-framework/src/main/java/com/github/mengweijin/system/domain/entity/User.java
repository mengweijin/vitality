package com.github.mengweijin.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("VTL_USER")
public class User extends BaseEntity {

    /**
    * 用户登录名（字母数字下划线）
    */
    private String username;

    /**
    * 用户昵称
    */
    private String nickname;

    /**
    * 登录密码
    */
    private String password;

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
    * 2FA（two-factor authentication）双重身份验证密钥
    */
    private String secretKey;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 逻辑删除。[Y, N]
    */
    private String deleted;
}
