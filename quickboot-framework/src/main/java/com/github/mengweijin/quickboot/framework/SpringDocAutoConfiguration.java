package com.github.mengweijin.quickboot.framework;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mengweijin
 * @date 2022/1/16
 */
@Configuration
@Profile({"!prod"})
@ConditionalOnClass({GroupedOpenApi.class})
public class SpringDocAutoConfiguration {

    /**
     * 可以多添加几个这样的 bean,
     * 按照 .pathsToMatch(Constants.ALL_PATTERN)
     * 或者 .packagesToScan("com.github.mengweijin")
     * 来分组展示。
     */
    @Bean
    public GroupedOpenApi applicationAllApi(AppInfoProperties appInfoProperties) {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch(Constants.ALL_PATTERN)
                .addOpenApiCustomiser(openApi ->
                        openApi.info(new Info().title("All APIs")
                                .version(appInfoProperties.getVersion()))
                )
                .build();
    }
}
