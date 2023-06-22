package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
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
     * 加盐
     */
    @TableField("PWD_SALT")
    private String pwdSalt;

    /**
     * 用户昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 身份证号
     */
    @TableField("ID_CARD_NUMBER")
    private String idCardNumber;
    /**
     * 性别。关联数据字典：user_gender
     */
    @TableField("GENDER")
    private String gender;

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
    private Integer disabled;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("DELETED")
    private Integer deleted;

}
