package com.github.mengweijin.quickboot.framework.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mengweijin
 */
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;
    /**
     * data
     */
    private T data;
    /**
     * Error message
     */
    private String message;

    private LocalDateTime time;

    private R() {
    }

    public static <T> R<T> success() {
        return success("SUCCESS");
    }

    public static <T> R<T> success(String message) {
        return info(HttpStatus.OK.value(), message, null);
    }

    public static <T> R<T> success(int code, String message) {
        return info(code, message, null);
    }

    public static <T> R<T> success(String message, T data) {
        return info(HttpStatus.OK.value(), message, data);
    }

    public static <T> R<T> error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static <T> R<T> error(String message) {
        return info(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> R<T> error(int code, String message) {
        return info(code, message, null);
    }

    public static <T> R<T> info(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        r.setTime(LocalDateTime.now());
        return r;
    }

    /**
     * add a message
     *
     * @param message message
     * @return this
     */
    public R<T> addMessage(String message) {
        if(this.message == null) {
            this.message = message;
        } else {
            this.message += " | " + message;
        }
        return this;
    }
}
