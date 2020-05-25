package com.github.mengweijin.quickboot.framework.exception;


/**
 * @author Meng Wei Jin
 * @description 应用
 **/
public class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
