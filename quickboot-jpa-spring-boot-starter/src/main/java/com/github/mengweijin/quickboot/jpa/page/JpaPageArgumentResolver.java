package com.github.mengweijin.quickboot.jpa.page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 **/
public class JpaPageArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断是否支持要转换的参数类型
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Pager.class);
    }

    /**
     * 当支持后进行相应的转换
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String currentString = StrUtil.blankToDefault(request.getParameter("current"), String.valueOf(Pager.CURRENT));
        String sizeString = StrUtil.blankToDefault(request.getParameter("size"), String.valueOf(Pager.SIZE));

        int current = NumberUtil.parseInt(currentString);
        int size = NumberUtil.parseInt(sizeString);

        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        current = current <= 0 ? 0 : current - 1;
        return new Pager<>().setCurrent(current).setSize(size);
    }
}