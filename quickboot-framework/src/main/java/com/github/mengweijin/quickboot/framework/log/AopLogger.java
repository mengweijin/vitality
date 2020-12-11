package com.github.mengweijin.quickboot.framework.log;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author mengweiijin
 */
@Data
@Accessors(chain = true)
public class AopLogger implements Serializable {

    private static final long serialVersionUID = 8755408793880948573L;

    /**
     * request url
     */
    private String url;

    /**
     * Http request method
     */
    private String httpMethod;

    /**
     * request parameters
     */
    private String requestParameter;

    /**
     * request methodName
     */
    private String methodName;

    /**
     * operate time UTC
     */
    private ZonedDateTime operateUtcTime;

    /**
     * operate time local
     */
    private LocalDateTime operateLocalTime;

    /**
     * operate IP
     */
    private String operateIP;

    /**
     * Operate status, Const.SUCCESS, Const.FAILURE
     */
    private String status;

    /**
     * error information
     */
    private String errorInfo;

    /**
     * response object in json format
     */
    private String responseJson;

}
