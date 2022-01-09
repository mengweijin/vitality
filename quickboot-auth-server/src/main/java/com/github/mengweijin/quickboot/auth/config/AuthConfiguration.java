package com.github.mengweijin.quickboot.auth.config;

import com.github.mengweijin.quickboot.auth.controller.AuthController;
import com.github.mengweijin.quickboot.auth.properties.AuthProperties;
import com.github.mengweijin.quickboot.framework.AppInfoProperties;
import com.github.mengweijin.quickboot.framework.util.SpringDocUtils;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mengweijin
 * @date 2022/1/7
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration {

    @Autowired
    private AppInfoProperties appInfoProperties;

    private static final String PACKAGE_AUTH = SpringDocUtils.getParentPackagePath(AuthController.class);

    @Bean
    @Profile({"!prod"})
    @ConditionalOnClass(GroupedOpenApi.class)
    public GroupedOpenApi authApi() {
        String groupName = SpringDocUtils.createGroupName(PACKAGE_AUTH);
        return GroupedOpenApi.builder().group(groupName)
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title(groupName).version(appInfoProperties.getVersion())))
                .packagesToScan(PACKAGE_AUTH)
                .build();
    }
}
