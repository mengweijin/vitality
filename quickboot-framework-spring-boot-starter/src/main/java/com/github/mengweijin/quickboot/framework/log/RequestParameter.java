package com.github.mengweijin.quickboot.framework.log;

import lombok.Data;

import java.util.Map;

/**
 * @author mengweijin
 */
@Data
public class RequestParameter {

    /**
     * Args from Controller Method or URL
     */
    private Map<String, String[]> urlArgs;

    /**
     * Args from Http Request Body
     */
    private Object[] requestBodyArgs;
}
