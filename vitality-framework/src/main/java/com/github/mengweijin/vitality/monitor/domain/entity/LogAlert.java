package com.github.mengweijin.vitality.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
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
@EqualsAndHashCode(callSuper = true)
@TableName("VTL_LOG_ALERT")
public class LogAlert extends BaseEntity {

    /**
     * 日志级别
     */
    private String logLevel;

    /**
     * 日志信息
     */
    private String message;

    /**
    * 类名称
    */
    private String className;

    /**
    * 方法名称
    */
    private String methodName;

    /**
    * 异常类型
    */
    private String exceptionName;

    /**
    * 异常堆栈信息
    */
    private String stackTrace;
}
