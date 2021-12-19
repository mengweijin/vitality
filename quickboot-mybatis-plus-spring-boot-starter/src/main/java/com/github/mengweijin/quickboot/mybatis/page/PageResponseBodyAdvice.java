package com.github.mengweijin.quickboot.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.core.MethodParameter;
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
public class PageResponseBodyAdvice implements ResponseBodyAdvice<IPage> {

    /**
     * 判断是否支持要转换的参数类型
     *
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return IPage.class.isAssignableFrom(methodParameter.getParameterType());
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
    public IPage<?> beforeBodyWrite(IPage page, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Pager<?> pager = new Pager<>();
        pager.setCurrent(page.getCurrent()).setSize(page.getSize()).setTotal(page.getTotal());
        pager.setDataList(page.getRecords());
        return pager;
    }
}
