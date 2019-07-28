package com.mwj.mwjwork.common.exception;


/**
 * @author Meng Wei Jin
 * @description 应用
 **/
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
