package com.github.mengweijin.quickboot.auth.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QB_USER")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号/用户名
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @TableField("PASSWORD")
    private String password;

    /**
     * 用户昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 逻辑删除。帐号状态。0 or 1
     */
    @TableField("DELETED")
    private Integer deleted;

}
