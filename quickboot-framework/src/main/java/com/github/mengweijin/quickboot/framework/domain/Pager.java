package com.github.mengweijin.quickboot.framework.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Collections;
import java.util.List;

/**
 * @author mengweijin
 * @date 2021/12/26
 */
@Data
@Accessors(chain = true)
public class Pager<T> {

    /**
     * 查询数据列表
     */
    List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    long total = 0;

    /**
     * 每页显示条数，默认 10
     */
    long size = 10;

    /**
     * 当前页
     */
    long current = 1;
}
