package com.github.mengweijin.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

/**
 * application.yaml
 * xss:
 *   # 是否启用xss过滤，default true.
 *   enabled: true
 *   # 不需要xss校验的链接（配置示例：/system/*,/tool/*）
 *   #excludes: /druid/*,/actuator/*
 * @author Meng Wei Jin
 * @description 加@Validated可对配置属性进行数据格式校验
 **/
@ConfigurationProperties(prefix = "xss")
@Data
@Validated
public class XssProperties {

    /**
     * xss过滤开关.
     */
    private Boolean enabled = true;

    /**
     * 不需要xss校验的链接
     * <p>
     * 配置示例：
     * quickboot:
     * xss:
     * - /system/*
     * - /tool/*
     */
    private List<String> excludes = new ArrayList<>();

}
