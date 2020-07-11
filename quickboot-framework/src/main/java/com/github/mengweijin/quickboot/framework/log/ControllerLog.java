package com.github.mengweijin.quickboot.framework.log;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mengweiijin
 */
@Data
@Accessors(chain = true)
public class ControllerLog implements Serializable {

    private static final long serialVersionUID = 8755408793880948573L;

    /**
     * request url
     */
    private String url;

    /**
     * request parameters
     */
    private String requestParameter;

    /**
     * request method
     */
    private String method;

    /**
     * operate time
     */
    private LocalDateTime operateTime;

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
     * response object
     */
    private Object responseObject;

}
