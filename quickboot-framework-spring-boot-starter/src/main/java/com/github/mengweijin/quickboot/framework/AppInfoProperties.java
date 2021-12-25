package com.github.mengweijin.quickboot.framework;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

/**
 * 加@Validated可对配置属性进行数据格式校验
 * @author Meng Wei Jin
 **/
@ConfigurationProperties(prefix = "info.app")
@Data
@Validated
public class AppInfoProperties {

    private String groupId;

    private String artifactId;

    private String version;

    /**
     * 为什么添加这个属性？
     * 因为在多模块项目下，有可能你的子模块 maven 中没有配置 groupId, 因此就获取不到上面的 groupId。
     * 但由于 maven 默认子模块和父模块的 groupId 是相同的，因此，当获取不到 groupId 时，可以从 parentGroupId 获取。
     */
    private String parentGroupId;

}
