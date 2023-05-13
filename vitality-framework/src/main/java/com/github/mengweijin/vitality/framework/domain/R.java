package com.github.mengweijin.vitality.framework.domain;

import com.github.mengweijin.vitality.framework.constant.Const;
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
public class R implements Serializable {

    /**
     * 状态码
     */
    private int code;
    /**
     * data
     */
    private Object data;
    /**
     * Error message
     */
    private String message;

    private LocalDateTime time;

    private R() {
    }

    public static R success() {
        return success(null);
    }

    public static R success(Object data) {
        return success(HttpStatus.OK.value(), data);
    }

    public static R success(int code, Object data) {
        return info(code, Const.SUCCESS, data);
    }

    public static R error() {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static R error(String message) {
        return info(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static R error(int code, String message) {
        return info(code, message, null);
    }

    public static R info(int code, String msg, Object data) {
        R r = new R();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        r.setTime(LocalDateTime.now());
        return r;
    }

    public static R bool(boolean flag) {
        return flag ? R.success() : R.error(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase() + " or Business Failed.");
    }

    /**
     * add a message
     *
     * @param message message
     */
    public void appendMessage(String message) {
        this.message = this.message == null ? message : (this.message +  " | " + message);
    }
}
