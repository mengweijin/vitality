package com.github.mengweijin.vitality.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vitality.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
@TableName("VTL_LOG_ERROR")
public class LogError extends BaseEntity {

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
    * 异常信息
    */
    private String errorMsg;

    /**
    * 异常堆栈信息
    */
    private String stackTrace;
}
