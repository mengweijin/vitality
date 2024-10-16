package com.github.mengweijin.vitality.framework.mvc;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.exception.BusinessException;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.exception.LoginFailedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Meng Wei Jin
 * 自定义异常处理器
 * 注意:
 * 1. 不要重复定义异常捕获，对于父类里已经定义好的，只要overwrite就好，不要重复声明异常拦截。
 * 2. @ExceptionHandler注解的方法参数不能添加 HttpHeaders 类型的参数，否则异常无法被拦截到。
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseResponseEntityExceptionHandler {

    @ExceptionHandler({
            ValidationException.class,
            ClientException.class
    })
    @ResponseBody
    ResponseEntity<R> handleClientException(Exception e, HttpServletRequest request) {
        log.warn(e.getMessage());
        R r = R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
    }

    @ExceptionHandler({ LoginFailedException.class })
    @ResponseBody
    ResponseEntity<R> handleNotLoginException(LoginFailedException e, HttpServletRequest request) {
        R r = R.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
    }

    @ExceptionHandler({ NotLoginException.class })
    @ResponseBody
    ResponseEntity<R> handleNotLoginException(NotLoginException e, HttpServletRequest request) {
        R r = R.error(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(r);
    }

    @ExceptionHandler({ NotPermissionException.class })
    @ResponseBody
    ResponseEntity<R> handleNotLoginException(NotPermissionException e, HttpServletRequest request) {
        R r = R.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(r);
    }

    @ExceptionHandler({BusinessException.class, RuntimeException.class, Exception.class})
    @ResponseBody
    ResponseEntity<R> handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        HttpStatus status = getStatus(request);
        return ResponseEntity.status(status).body(R.error(status.value(), e.getMessage()));
    }

    /**
     * 获取请求状态码
     *
     * @param request HttpServletRequest
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.valueOf(statusCode);
    }
}
