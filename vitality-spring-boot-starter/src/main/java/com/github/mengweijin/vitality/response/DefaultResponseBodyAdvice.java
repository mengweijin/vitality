package com.github.mengweijin.vitality.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vitality.VitalityProperties;
import com.github.mengweijin.vitality.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * enable response body advice.
 * @Bean
 * @ConditionalOnMissingBean
 * public DefaultResponseBodyAdvice defaultResponseBodyAdvice() {
 *     return new DefaultResponseBodyAdvice();
 * }
 * @author mengweijin
 * @date 2022/5/17
 */
@Slf4j
@RestControllerAdvice
public class DefaultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private VitalityProperties vitalityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Supports responses to all requests
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return true
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpMethod httpMethod = request.getMethod();
        String path = request.getURI().getPath();
        boolean match = vitalityProperties.getBodyAdviceExcludePathPrefix().stream().anyMatch(item -> path.toLowerCase().startsWith(item.toLowerCase()));
        if(match) {
            return body;
        }

        if(HttpMethod.POST == httpMethod
            || HttpMethod.PUT == httpMethod
            || HttpMethod.DELETE == httpMethod
            || HttpMethod.PATCH == httpMethod) {
            if(body instanceof R || body instanceof String) {
                return body;
            // } else if(body instanceof String) {
                // 这段代码一定要加，如果Controller直接返回String的话，SpringBoot是直接返回，故我们需要手动转换成json。
                // springmvc数据转换器对String是有特殊处理 StringHttpMessageConverter
                // try {
                //     return objectMapper.writeValueAsString(R.success(body));
                // } catch (JsonProcessingException e) {
                //     log.error("An exception has occurred that should not have occurred!", e);
                //     throw new RuntimeException(e.getMessage());
                // }
            } else {
                return R.success(body);
            }
        }
        return body;
    }
}
