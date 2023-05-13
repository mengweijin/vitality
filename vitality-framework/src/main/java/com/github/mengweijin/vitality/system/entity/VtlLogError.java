package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_ERROR")
public class VtlLogError extends BaseEntity {

    @TableField("CLASS_NAME")
    private String className;

    @TableField("METHOD_NAME")
    private String methodName;

    @TableField("EXCEPTION_NAME")
    private String exceptionName;

    @TableField("ERROR_MSG")
    private String errorMsg;

    @TableField("STACK_TRACE")
    private String stackTrace;

}
