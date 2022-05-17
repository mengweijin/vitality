package com.github.mengweijin.quickboot.framework.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author mengweijin
 * @date 2022/5/17
 */
@RestControllerAdvice
public class DefaultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

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
        if(body instanceof R) {
            return body;
        } else if(body instanceof String) {
            // 这段代码一定要加，如果Controller直接返回String的话，SpringBoot是直接返回，故我们需要手动转换成json。
            // springmvc数据转换器对String是有特殊处理 StringHttpMessageConverter
            try {
                return objectMapper.writeValueAsString(R.success(body));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return R.success(body);
    }
}
