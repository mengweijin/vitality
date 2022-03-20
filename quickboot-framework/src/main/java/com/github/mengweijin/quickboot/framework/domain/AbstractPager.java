package com.github.mengweijin.quickboot.framework.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2021/12/26
 */
@Data
@Accessors(chain = true)
public abstract class AbstractPager<T> {

    /**
     * 查询数据列表
     */
    private List<T> records = new ArrayList<>();

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

}
