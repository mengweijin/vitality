package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.vitality.system.enums.EGender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_USER")
public class VtlUser extends BaseEntity {

    /**
     * 用户登录名（字母数字下划线）
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 登录密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 用户昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 性别 { MALE: 男；FEMALE：女；OTHER：其他；}
     */
    @TableField("GENDER")
    private EGender gender;

    /**
     * 电子邮箱
     */
    @TableField("EMAIL")
    private String email;

    /**
     * 移动电话
     */
    @TableField("MOBILE_PHONE")
    private String mobilePhone;

    /**
     * 2FA（two-factor authentication）双重身份验证密钥
     */
    @TableField("SECRET_KEY_2FA")
    private String secretKey2fa;

    /**
     * 是否已禁用。{ 0：正常；1：禁用；}
     */
    @TableField("DISABLED")
    private Boolean disabled;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("DELETED")
    private Boolean deleted;

}