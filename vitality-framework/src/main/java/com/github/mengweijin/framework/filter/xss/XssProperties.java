package com.github.mengweijin.framework.filter.xss;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

/**
 * application.yaml
 * vitality:
 *   xss:
 *     # 是否启用xss过滤，default false.
 *     enabled: true
 *     # 不需要xss校验的链接（配置示例：/system/*,/tool/*）
 *     #excludes: /druid/*,/actuator/*
 * @author Meng Wei Jin
 * 加@Validated可对配置属性进行数据格式校验
 **/
@Data
@Validated
public class XssProperties {

    /**
     * xss过滤开关.
     */
    private Boolean enabled = false;

    /**
     * 不需要xss校验的链接
     * <p>
     * 配置示例：
     * vitality:
     *   xss:
     *     - /system/*
     *     - /tool/*
     * </p>
     */
    private List<String> excludes = new ArrayList<>();

}
