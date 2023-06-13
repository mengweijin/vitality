package com.github.mengweijin.vitality.framework.frontend.tinymce;

import lombok.Data;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
@Data
public class TinymceImageResponse {

    private Integer code;

    private String msg;

    private Object data;
    private TinymceImageResponse() {
    }

    public static TinymceImageResponse info(Object data, int code, String msg) {
        TinymceImageResponse r = new TinymceImageResponse();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static TinymceImageResponse success(Object data) {
        return info(data, 0, "SUCCESS");
    }

    public static TinymceImageResponse failed(int code, String message) {
        return info(null, code, message);
    }

    public static TinymceImageResponse failed(String message) {
        return info(null, 500, message);
    }
}
