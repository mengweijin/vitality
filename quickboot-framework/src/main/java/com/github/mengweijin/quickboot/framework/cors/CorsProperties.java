package com.github.mengweijin.quickboot.framework.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author mengweijin
 */
@Data
@ConfigurationProperties("quickboot.cors")
public class CorsProperties {

    /**
     * Whether to enable auto-configuration.
     */
    private boolean enabled = true;

    /**
     * url white list
     */
    private List<String> urlWhiteList;

    /**
     * header white list
     * http header
     */
    private List<String> headerWhiteList;

    /**
     * method white list
     * GET, POST, PATCH, PUT, DELETE等
     */
    private List<String> methodWhiteList;

}
