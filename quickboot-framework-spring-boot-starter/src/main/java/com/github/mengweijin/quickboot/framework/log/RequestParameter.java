package com.github.mengweijin.quickboot.framework.log;

import lombok.Data;

import java.util.Map;

/**
 * @author mengweijin
 */
@Data
public class RequestParameter {

    /**
     * Args from Http Request
     */
    private Map<String, String[]> requestArgs;

    /**
     * Args from Controller Method or URL
     */
    private Object[] args;
}
