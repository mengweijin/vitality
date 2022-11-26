package com.github.mengweijin.vitality.layui.model;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
@Data
public class LayuiTable<T> {

    private List<T> data;

    private Long count;

    private Integer code;

    private String msg;

    public LayuiTable() {
    }

    public LayuiTable(List<T> data) {
        this(data, data == null ? 0 : data.size());
    }

    public LayuiTable(List<T> data, long count) {
        this(data, count, 0, "SUCCESS");
    }

    public LayuiTable(List<T> data, long count, int code, String msg) {
        this.data = data;
        this.count = count;
        this.code = code;
        this.msg = msg;
    }
}
