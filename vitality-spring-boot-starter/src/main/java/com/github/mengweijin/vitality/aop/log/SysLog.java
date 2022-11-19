package com.github.mengweijin.vitality.aop.log;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author mengweiijin
 */
@Data
@Accessors(chain = true)
public class SysLog {

    /** request url */
    private String url;

    /** Http request method */
    private String httpMethod;

    /** request methodName */
    private String methodName;

    /** operate IP */
    private String ip;

    /** Operate status */
    private Boolean success;

    /** error information */
    private String error;

    /** operate time */
    private LocalDateTime operateTime;

    /** Operate user id */
    private String operator;
}
