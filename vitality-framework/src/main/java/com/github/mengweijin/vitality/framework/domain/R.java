package com.github.mengweijin.vitality.framework.domain;

import com.github.mengweijin.vitality.framework.constant.Const;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mengweijin
 */
@Data
@SuppressWarnings({"unused"})
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
    private String msg;

    private LocalDateTime time;

    private R() {
    }

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> success(T data) {
        return success(HttpStatus.OK.value(), data);
    }

    public static <T> R<T> success(int code, T data) {
        return ajax(code, Const.SUCCESS, data);
    }

    public static <T> R<T> failure() {
        return ajax(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    public static <T> R<T> failure(int code) {
        return ajax(code, null, null);
    }

    public static <T> R<T> failure(String message) {
        return ajax(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> R<T> failure(int code, String message) {
        return ajax(code, message, null);
    }

    public static <T> R<T> ajax(int code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setTime(LocalDateTime.now());
        return r;
    }

    public static <T> R<T> ajax(boolean flag) {
        return flag ? R.success() : R.failure(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase() + " or Business Failed.");
    }

    public static <T> R<T> ajax(int i) {
        return R.ajax(i > 0);
    }

    /**
     * add a message
     *
     * @param message message
     */
    public void appendMessage(String message) {
        this.msg = this.msg == null ? message : (this.msg +  " | " + message);
    }
}
