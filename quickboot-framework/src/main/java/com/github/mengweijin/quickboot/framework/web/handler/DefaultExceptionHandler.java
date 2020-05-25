package com.github.mengweijin.quickboot.framework.web.handler;

import com.github.mengweijin.quickboot.framework.exception.ClientException;
import com.github.mengweijin.quickboot.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author Meng Wei Jin
 * @description 自定义异常处理器
 * 注意，不要重复定义异常捕获，对于父类里已经定义好的，只要overwrite就好，不要重复声明异常拦截。
 **/
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends BaseResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, ClientException.class})
    @ResponseBody
    ResponseEntity<?> handleClientException(HttpServletRequest request, Throwable e, HttpHeaders headers) {
        log.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.BAD_REQUEST.value()).addMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(errorInfo);
    }

    @ExceptionHandler({ServerException.class, RuntimeException.class, Exception.class})
    @ResponseBody
    ResponseEntity<?> handleException(HttpServletRequest request, Throwable e, HttpHeaders headers) {
        log.error(e.getMessage(), e);
        HttpStatus status = getStatus(request);
        ErrorInfo errorInfo = new ErrorInfo().setCode(status.value()).addMessage(e.getMessage());
        return ResponseEntity.status(status).headers(headers).body(errorInfo);
    }

    /**
     * 获取请求状态码
     *
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
