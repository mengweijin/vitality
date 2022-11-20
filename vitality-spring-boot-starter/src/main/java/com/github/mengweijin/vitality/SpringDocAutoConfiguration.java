package com.github.mengweijin.vitality;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * <a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a>
 * @author mengweijin
 * @date 2022/1/16
 */
@Configuration
@Profile({"dev", "test"})
@ConditionalOnClass({GroupedOpenApi.class})
public class SpringDocAutoConfiguration {

    /**
     * 可以多添加几个这样的 bean,
     * 按照 .pathsToMatch(Constants.ALL_PATTERN)
     * 或者 .packagesToScan("com.github.mengweijin")
     * 来分组展示。
     */
    @Bean
    public GroupedOpenApi applicationAllApi() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch(Constants.ALL_PATTERN)
                .addOpenApiCustomiser(openApi ->
                        openApi.info(new Info().title("All APIs").version("Latest Version"))
                )
                .build();
    }
}
