package com.github.mengweijin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.framework.mybatis.entity.BaseEntity;
import com.github.mengweijin.system.enums.ELoginType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录日志记录表
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_LOGIN")
public class LogLoginDO extends BaseEntity {

    /**
     * 登录账号
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 登录类型。枚举类 ELoginType.java
     */
    @TableField("LOGIN_TYPE")
    private ELoginType loginType;

    /**
     * 登录IP地址
     */
    @TableField("IP")
    private String ip;

    /**
     * IP所属位置
     */
    @TableField("IP_LOCATION")
    private String ipLocation;

    /**
     * 浏览器
     */
    @TableField("BROWSER")
    private String browser;

    /**
     * 设备平台类型
     */
    @TableField("PLATFORM")
    private String platform;

    /**
     * 操作系统
     */
    @TableField("OPERATING_SYSTEM")
    private String operatingSystem;

    /**
     * 登录是否成功。{0=失败, 1=成功}
     */
    @TableField("SUCCEEDED")
    private Integer succeeded;

    /**
     * 错误消息
     */
    @TableField("ERROR_INFO")
    private String errorInfo;

}
