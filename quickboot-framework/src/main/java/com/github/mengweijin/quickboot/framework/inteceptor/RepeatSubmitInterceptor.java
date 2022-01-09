package com.github.mengweijin.quickboot.framework.inteceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mengweijin.quickboot.framework.domain.R;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 */
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    R<?> r = R.fail(HttpStatus.BAD_REQUEST.value(), annotation.message());
                    ServletUtils.render(response, r);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交由子类实现具体地防重复提交的规则
     *
     * @param request
     * @param repeatSubmit
     * @return
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit) throws JsonProcessingException;
}
