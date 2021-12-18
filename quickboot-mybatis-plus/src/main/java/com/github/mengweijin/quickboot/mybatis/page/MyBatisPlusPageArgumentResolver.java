package com.github.mengweijin.quickboot.mybatis.page;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.quickboot.framework.web.PageArgumentResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 * @description
 **/
public class MyBatisPlusPageArgumentResolver implements PageArgumentResolver {

    /**
     * 判断是否支持要转换的参数类型
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return IPage.class.isAssignableFrom(methodParameter.getParameterType());
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
    public Page<?> resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Page<?> page = new Page<>();
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        String currentString = StrUtil.blankToDefault(request.getParameter("current"), String.valueOf(page.getCurrent()));
        String sizeString = StrUtil.blankToDefault(request.getParameter("size"), String.valueOf(page.getSize()));
        String totalString = StrUtil.blankToDefault(request.getParameter("total"), String.valueOf(page.getTotal()));

        long current = NumberUtil.parseLong(currentString);
        long size = NumberUtil.parseLong(sizeString);
        long total = NumberUtil.parseLong(totalString);
        return page.setCurrent(current).setSize(size).setTotal(total);
    }
}
