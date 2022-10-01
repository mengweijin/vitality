package com.github.mengweijin.layui.model;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
@Data
public final class LayuiTable {

    private LayuiTable() {}

    private Integer code;

    private String msg;

    private List<?> data;

    private Long count;

    public static <T> LayuiTable data(List<T> data, long count) {
        return data(data, count, "SUCCESS");
    }

    public static <T> LayuiTable data(List<T> data, long count, String message) {
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg(message);
        layuiTable.setData(data);
        layuiTable.setCount(count);
        return layuiTable;
    }
}
