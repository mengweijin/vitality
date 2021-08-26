package com.github.mengweijin.quickboot.framework.log;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 */
@Data
public class RequestParameter {

    /**
     * Args from Request Args
     */
    private Map<String, String[]> requestArgs;

    /**
     * Args from Controller Method Args
     */
    private List<Map<String, Object>> methodArgs;
}
