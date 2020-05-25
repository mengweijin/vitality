package com.github.mengweijin.mwjwork.mybatis.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.quickboot.framework.web.PagerArgumentResolver;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Component
public class MybatisPagerArgumentResolver extends PagerArgumentResolver {

    /**
     * 判断是否支持要转换的参数类型
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
        long current = NumberUtils.toLong(request.getParameter("current"), Pager.CURRENT);
        long size = NumberUtils.toLong(request.getParameter("size"), Pager.SIZE);
        long total = NumberUtils.toLong(request.getParameter("total"), Pager.TOTAL);
        return new Page(current, size, total);
    }
}
