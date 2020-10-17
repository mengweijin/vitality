package com.github.mengweijin.quickboot.jpa.page;

import com.github.mengweijin.quickboot.framework.web.PagerArgumentResolver;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Configuration
public class QuickBootJpaPagerArgumentResolverAutoConfiguration extends PagerArgumentResolver {

    /**
     * 判断是否支持要转换的参数类型
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Pager.class);
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

        int current = NumberUtils.toInt(request.getParameter("current"), Pager.CURRENT);
        int size = NumberUtils.toInt(request.getParameter("size"), Pager.SIZE);

        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        current = current <= 0 ? 0 : current - 1;
        return new Pager<>().setCurrent(current).setSize(size);
    }
}
