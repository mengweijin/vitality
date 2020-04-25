package com.mengweijin.mwjwork.framework.web;

import com.mengweijin.mwjwork.framework.exception.ClientException;
import com.mengweijin.mwjwork.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 自定义异常处理器
 * 注意，不要重复定义异常捕获，对于父类里已经定义好的，只要overwrite就好，不要重复声明异常拦截。
 **/
@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, ClientException.class})
    @ResponseBody
    ResponseEntity<?> handleClientException(HttpServletRequest request, Throwable e) {
        log.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.BAD_REQUEST.value()).addMessage(e.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServerException.class, RuntimeException.class, Exception.class})
    @ResponseBody
    ResponseEntity<?> handleException(HttpServletRequest request, Throwable e) {
        log.error(e.getMessage(), e);
        HttpStatus status = getStatus(request);
        ErrorInfo errorInfo = new ErrorInfo().setCode(status.value()).addMessage(e.getMessage());
        return new ResponseEntity<>(errorInfo, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.BAD_REQUEST.value());
        for (FieldError error: fieldErrors) {
            errorInfo.addMessage(error.getField() + ": " + error.getDefaultMessage()+"!");
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        ErrorInfo errorInfo = new ErrorInfo().setCode(HttpStatus.BAD_REQUEST.value());
        for (FieldError error: fieldErrors) {
            errorInfo.addMessage(error.getField() + ": " + error.getDefaultMessage()+"!");
        }

        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
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
