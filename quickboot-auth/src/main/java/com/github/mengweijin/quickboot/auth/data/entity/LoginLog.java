package com.github.mengweijin.quickboot.auth.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.quickboot.auth.enums.LoginType;
import com.github.mengweijin.quickboot.auth.enums.LoginStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Data
@Accessors(chain = true)
@TableName("QB_LOGIN_LOG")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 自动生成主键，子类不可重写，但可以在项目中重新定义一个BaseEntity。
     * JsonSerialize：JavaScript 无法处理 Java 的长整型 Long 导致精度丢失，具体表现为主键最后两位永远为 0
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

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
