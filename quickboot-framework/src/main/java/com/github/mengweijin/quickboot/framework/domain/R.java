package com.github.mengweijin.quickboot.framework.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Error message list
     */
    private List<String> msgList;

    private R() {
    }

    public static <T> R<T> ok() {
        return msg(HttpStatus.OK.value(), null);
    }

    public static <T> R<T> ok(T data) {
        return msg(HttpStatus.OK.value(), data);
    }

    public static <T> R<T> ok(T data, String msg) {
        return msg(HttpStatus.OK.value(), data, msg);
    }

    public static <T> R<T> fail() {
        return msg(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

    public static <T> R<T> fail(T data) {
        return msg(HttpStatus.INTERNAL_SERVER_ERROR.value(), data);
    }

    public static <T> R<T> fail(T data, String msg) {
        return msg(HttpStatus.INTERNAL_SERVER_ERROR.value(), data, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return msg(code, null, msg);
    }

    public static <T> R<T> msg(int code, T data, String... msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsgList(msg == null ? null : Arrays.asList(msg));
        return r;
    }

    /**
     * add a message
     *
     * @param msg message
     * @return this
     */
    public R<T> addMessage(String msg) {
        this.msgList = this.msgList == null ? new ArrayList<>() : this.msgList;
        this.msgList.add(msg);
        return this;
    }
}
