package com.github.mengweijin.quickboot.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.quickboot.auth.enums.LoginType;
import com.github.mengweijin.quickboot.auth.enums.LoginStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("QB_LOGIN_LOG")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账号
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 登录类型（LOGIN or LOGOUT）。 枚举类 LoginType
     */
    @TableField("LOGIN_TYPE")
    private LoginType loginType;

    /**
     * 登录状态（SUCCESS or FAILURE）。 枚举类 LoginStatus
     */
    @TableField("LOGIN_STATUS")
    private LoginStatus loginStatus;

    /**
     * 登录/登出IP地址
     */
    @TableField("IP")
    private String ip;

    /**
     * 操作系统
     */
    @TableField("OS")
    private String os;

    /**
     * 浏览器类型
     */
    @TableField("BROWSER")
    private String browser;

    /**
     * 提示消息
     */
    @TableField("MESSAGE")
    private String message;

}
