package com.github.mengweijin.vitality.framework.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.framework.constant.Const;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表格数据响应对象
 *
 * @author mengweijin
 */
@Data
@NoArgsConstructor
@SuppressWarnings({"unused"})
public class TableInfo<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总数
     */
    private long total;

    /**
     * 数据
     */
    private List<T> rows;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    public static <T> TableInfo<T> build(IPage<T> page) {
        TableInfo<T> data = new TableInfo<>();
        data.setCode(HttpStatus.OK.value());
        data.setMsg(Const.SUCCESS);
        data.setRows(page.getRecords());
        data.setTotal(page.getTotal());
        return data;
    }

    public static <T> TableInfo<T> build(List<T> list) {
        return build(list, list.size());
    }

    public static <T> TableInfo<T> build(List<T> list, long total) {
        TableInfo<T> data = new TableInfo<>();
        data.setCode(HttpStatus.OK.value());
        data.setMsg(Const.SUCCESS);
        data.setRows(list);
        data.setTotal(total);
        return data;
    }

}
