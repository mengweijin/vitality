package com.github.mengweijin.quickboot.framework.xss;

import com.github.mengweijin.quickboot.framework.constant.Const;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Meng Wei Jin
 * @description
 *
 * 加@Validated可对配置属性进行数据格式校验
 **/
@ConfigurationProperties(prefix = "quickboot.xss")
@Data
@Validated
public class XssProperties {

    /**
     * xss过滤开关.
     */
    private Boolean enabled = true;

    /**
     * 不需要xss校验的链接（配置示例：/system/*,/tool/*）
     */
    private String excludes = Const.EMPTY;

}
