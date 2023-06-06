package com.github.mengweijin.vitality.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统错误日志记录表
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_ERROR")
public class VtlLogError extends BaseEntity {

    /**
     * 类名称
     */
    @TableField("CLASS_NAME")
    private String className;

    /**
     * 方法名称
     */
    @TableField("METHOD_NAME")
    private String methodName;

    /**
     * 异常类型
     */
    @TableField("EXCEPTION_NAME")
    private String exceptionName;

    /**
     * 异常信息
     */
    @TableField("ERROR_MSG")
    private String errorMsg;

    /**
     * 异常堆栈信息
     */
    @TableField("STACK_TRACE")
    private String stackTrace;

}
