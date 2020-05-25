package com.github.mengweijin.quickboot.framework.exception;


/**
 * @author Meng Wei Jin
 * @description Client Exception
 **/
public class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
