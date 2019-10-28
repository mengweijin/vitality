package com.mwj.mwjwork.framework.web;

import com.mwj.mwjwork.common.exception.SystemException;
import com.mwj.mwjwork.framework.web.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Meng Wei Jin
 * @description 自定义异常处理器
 * 注意，不要重复定义异常捕获，对于父类里已经定义好的，只要overwrite就好，不要重复声明异常拦截。
 **/
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            SystemException.class,
            RuntimeException.class,
            Exception.class
    })
    @ResponseBody
    ResponseEntity handleException(HttpServletRequest request, Throwable e) {
        log.error(e.getMessage(), e);
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(Result.error(status.value(), e.getMessage()), status);
    }

    /**
     * 获取请求状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
