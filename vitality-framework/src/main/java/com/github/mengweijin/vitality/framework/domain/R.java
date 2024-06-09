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
public class R<T> implements Serializable {

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

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> success(Object data) {
        return success(HttpStatus.OK.value(), data);
    }

    public static <T> R<T> success(int code, Object data) {
        return info(code, Const.SUCCESS, data);
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

    public static <T> R<T> info(int code, String msg, Object data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(msg);
        r.setData(data);
        r.setTime(LocalDateTime.now());
        return r;
    }

    public static <T> R<T> bool(boolean flag) {
        return flag ? R.success() : R.error(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase() + " or Business Failed.");
    }

    public static <T> R<T> ajax(int i) {
        return R.bool(i > 0);
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
