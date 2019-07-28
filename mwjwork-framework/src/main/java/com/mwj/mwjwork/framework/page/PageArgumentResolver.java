package com.mwj.mwjwork.framework.page;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Component
public class PageArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断是否支持要转换的参数类型
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Pager.class.isAssignableFrom(methodParameter.getParameterType());
    }

    /**
     * 当支持后进行相应的转换
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        // layui page parameter
        String currentStr = request.getParameter(Pager.VAR_PAGE);
        String sizeStr = request.getParameter(Pager.VAR_LIMIT);

        // convert default page parameter
        int page =  NumberUtils.toInt(currentStr, Pager.DEFAULT_PAGE);
        int limit = NumberUtils.toInt(sizeStr, Pager.DEFAULT_LIMIT);

        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        page = page < 0 ? 0 : page - 1;

        return new Pager().setPage(page).setLimit(limit);
    }
}
