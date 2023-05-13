package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweiijin
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("VTL_LOG_OPERATION")
public class VtlLogOperation extends BaseEntity {

    @TableField("URL")
    private String url;

    @TableField("HTTP_METHOD")
    private String httpMethod;

    @TableField("METHOD_NAME")
    private String methodName;

    @TableField("IP")
    private String ip;

    @TableField("IP_LOCATION")
    private String ipLocation;

    @TableField("IS_SUCCESS")
    private Boolean success;

    @TableField("ERROR_INFO")
    private String errorInfo;

}
