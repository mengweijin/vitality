package com.github.mengweijin.layui.model;

import lombok.Data;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/9/3
 */
@Data
public final class LayuiTableData {

    private LayuiTableData() {}

    private Integer code;

    private String msg;

    private List<?> data;

    private Integer count;


    public static <T> LayuiTableData data(List<T> data) {
        return data(data, "SUCCESS");
    }

    public static <T> LayuiTableData data(List<T> data, String message) {
        LayuiTableData layuiTableData = new LayuiTableData();
        layuiTableData.setCode(0);
        layuiTableData.setMsg(message);
        layuiTableData.setData(data);
        layuiTableData.setCount(data == null ? 0 : data.size());
        return layuiTableData;
    }
}
