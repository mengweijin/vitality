package com.github.mengweijin.vitality.framework.mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.vitality.framework.VitalityProperties;
import com.github.mengweijin.vitality.framework.domain.R;
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
@Deprecated
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
        if(body instanceof R || body instanceof String) {
            return body;
        }

        String path = request.getURI().getPath();
        boolean match = vitalityProperties.getBodyAdviceExcludePathPrefix().stream()
                .anyMatch(item -> path.toLowerCase().startsWith(item.toLowerCase()));
        if(match) {
            return body;
        }

        HttpMethod httpMethod = request.getMethod();
        if(HttpMethod.GET != httpMethod) {
            return R.success(body);
        }

        return body;
    }

    @Deprecated
    private Object returnStringToR(Object body) {
        if(body instanceof String) {
            // 这段代码一定要加，如果Controller直接返回String的话，SpringBoot是直接返回，故我们需要手动转换成json。
            // springmvc数据转换器对String是有特殊处理 StringHttpMessageConverter
            try {
                return objectMapper.writeValueAsString(R.success(body));
            } catch (JsonProcessingException e) {
                log.error("An exception has occurred that should not have occurred!", e);
                throw new RuntimeException(e.getMessage());
            }
        }
        return body;
    }
}
