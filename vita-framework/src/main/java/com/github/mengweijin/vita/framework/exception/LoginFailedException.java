package com.github.mengweijin.vita.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 在 SpringMVC 中，如果全局异常没有匹配到异常处理，那么当抛出这个自定义的异常时，SpringMVC 会读取 @ResponseStatus 注解的信息，并返回。
 * 所以，自定义异常类，也是一种处理全局异常的方式。并非只有 @ControllerAdvice，@ExceptionHandler
 *
 * @author Meng Wei Jin
 * Login Exception
 **/
@SuppressWarnings({"unused"})
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Login failed")
public class LoginFailedException extends RuntimeException {

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
