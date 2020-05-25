package com.github.mengweijin.app.videodownloader.exception;

import com.github.mengweijin.quickboot.framework.exception.ServerException;

/**
 * @author mengweijin
 */
public class M3U8Exception extends ServerException {

    public M3U8Exception(String message) {
        super(message);
    }

    public M3U8Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public M3U8Exception(Throwable cause) {
        super(cause);
    }
}
