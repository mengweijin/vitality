package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("VTL_LOG_LOGIN")
public class LogLogin extends BaseEntity {

    /**
    * 登录账号
    */
    private String username;

    /**
    * 登录类型。枚举类 ELoginType.java
    */
    private String loginType;

    /**
    * 登录IP地址
    */
    private String ip;

    /**
    * IP所属位置
    */
    private String ipLocation;

    /**
    * 浏览器
    */
    private String browser;

    /**
    * 设备平台类型
    */
    private String platform;

    /**
    * 操作系统
    */
    private String os;

    /**
    * 登录是否成功。[Y, N]
    */
    private String success;

    /**
    * 失败信息
    */
    private String errorMsg;

}
