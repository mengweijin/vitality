package com.github.mengweijin.quickboot.framework.doc;

import com.github.mengweijin.quickboot.framework.util.MavenUtils;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.apache.maven.model.Model;
import org.springdoc.core.Constants;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mengweijin
 * @date 2021/12/25
 */
@Profile({"!prod"})
@ConditionalOnClass(GroupedOpenApi.class)
@Configuration
public class SpringDocAutoConfiguration {

    @Bean
    public Model mavenModel() {
        return MavenUtils.readPom();
    }

    @Bean
    public GroupedOpenApi actuatorApi(OpenApiCustomiser actuatorOpenApiCustomiser,
                                      OperationCustomizer actuatorCustomizer,
                                      WebEndpointProperties endpointProperties,
                                      Model mavenModel) {
        return GroupedOpenApi.builder()
                .group("Actuator")
                .pathsToMatch(endpointProperties.getBasePath() + Constants.ALL_PATTERN)
                .addOpenApiCustomiser(actuatorOpenApiCustomiser)
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Actuator API").version(mavenModel.getVersion())))
                .addOperationCustomizer(actuatorCustomizer)
                .pathsToExclude(Constants.HEALTH_PATTERN)
                .build();
    }

    @Bean
    public GroupedOpenApi usersGroup(Model mavenModel) {
        return GroupedOpenApi.builder().group("users")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
                    return operation;
                })
                .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Users API").version(mavenModel.getVersion())))
                .packagesToScan("org.springdoc.demo.app2")
                .build();
    }
}
