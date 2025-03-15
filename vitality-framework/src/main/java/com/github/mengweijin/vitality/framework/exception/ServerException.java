package com.github.mengweijin.vitality.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 在 SpringMVC 中，如果全局异常没有匹配到异常处理，那么当抛出这个自定义的异常时，SpringMVC 会读取 @ResponseStatus 注解的信息，并返回。
 * 所以，自定义异常类，也是一种处理全局异常的方式。并非只有 @ControllerAdvice，@ExceptionHandler
 *
 * @author Meng Wei Jin
 * Server Exception
 **/
@SuppressWarnings({"unused"})
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
