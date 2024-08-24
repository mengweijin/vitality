package com.github.mengweijin.vitality.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

/**
 * 在 SpringMVC 中，如果全局异常没有匹配到异常处理，那么当抛出这个自定义的异常时，SpringMVC 会读取 @ResponseStatus 注解的信息，并返回。
 * 所以，自定义异常类，也是一种处理全局异常的方式。并非只有 @ControllerAdvice，@ExceptionHandler
 *
 * @author Meng Wei Jin
 * Client Exception
 **/
@SuppressWarnings({"unused"})
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class ClientException extends RestClientException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
