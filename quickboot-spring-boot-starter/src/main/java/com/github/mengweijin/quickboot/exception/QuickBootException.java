package com.github.mengweijin.quickboot.exception;

/**
 * @author Meng Wei Jin
 * 应用
 **/
public class QuickBootException extends RuntimeException {

    public QuickBootException(String message) {
        super(message);
    }

    public QuickBootException(Throwable cause) {
        super(cause);
    }

    public QuickBootException(String message, Throwable cause) {
        super(message, cause);
    }
}
