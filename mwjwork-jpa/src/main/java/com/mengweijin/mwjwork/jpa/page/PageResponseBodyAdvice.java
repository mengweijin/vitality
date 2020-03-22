package com.mengweijin.mwjwork.jpa.page;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Meng Wei Jin
 * @description
 **/
@ControllerAdvice
public class PageResponseBodyAdvice implements ResponseBodyAdvice<Page> {

    /**
     * 判断是否支持要转换的参数类型
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return Page.class.isAssignableFrom(methodParameter.getParameterType());
    }

    /**
     * 当支持后进行相应的转换
     * @param page
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Page beforeBodyWrite(Page page, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Pager pager = new Pager<>();
        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        pager.setCurrent(page.getNumber() + 1)
              .setSize(page.getSize())
              .setTotal(page.getTotalElements())
              .setDataList(page.getContent());
        return pager;
    }
}
