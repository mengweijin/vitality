package com.github.mengweijin.vitality.framework.exception;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
public class MinioServiceException extends RuntimeException {

    public MinioServiceException(String message) {
        super(message);
    }

    public MinioServiceException(Throwable cause) {
        super(cause);
    }

    public MinioServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
