package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统操作日志表
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_OPERATION")
public class VtlLogOperation extends BaseEntity {

    /**
     * 请求url
     */
    @TableField("URL")
    private String url;

    /**
     * 请求参数
     */
    @TableField("REQUEST_ARGS")
    private String requestArgs;

    /**
     * 请求 Body
     */
    @TableField("REQUEST_BODY")
    private String requestBody;
    /**
     * http 请求方式
     */
    @TableField("HTTP_METHOD")
    private String httpMethod;

    /**
     * 请求方法名称
     */
    @TableField("METHOD_NAME")
    private String methodName;

    /**
     * 浏览器
     */
    @TableField("BROWSER")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("OPERATING_SYSTEM")
    private String operatingSystem;

    /**
     * 设备平台类型
     */
    @TableField("PLATFORM")
    private String platform;

    /**
     * 操作IP地址
     */
    @TableField("IP")
    private String ip;

    /**
     * IP所属位置
     */
    @TableField("IP_LOCATION")
    private String ipLocation;

    /**
     * 操作是否成功。{0=失败, 1=成功}
     */
    @TableField("SUCCEEDED")
    private Integer succeeded;

    /**
     * 错误消息
     */
    @TableField("ERROR_INFO")
    private String errorInfo;

}
