package com.github.mengweijin.quickboot.framework.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweiijin
 */
@Data
@Accessors(chain = true)
public class AppLog {

    /**
     * request url
     */
    private String url;

    /**
     * Http request method
     */
    private String httpMethod;

    /**
     * Args from Controller Method or URL
     */
    private Map<String, String[]> args;

    /**
     * Args from Http Request Body
     */
    private HashMap<?, ?> requestBody;

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
    private String ip;

    /**
     * Operate status, Const.SUCCESS, Const.FAILURE
     */
    private String status;

    /**
     * error information
     */
    private String errorInfo;

    /**
     * response body object
     */
    private Object responseBody;

}
