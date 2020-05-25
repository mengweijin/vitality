package com.github.mengweijin.mwjwork.jpa.page;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 需要在前台设置请求参数名和返回的数据的参数名与当前类对应
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Pager<T> extends PageImpl<T> implements Serializable {

    public Pager() {
        super(Collections.emptyList());
    }

    /**
     * 默认当前页
     */
    public static final int CURRENT = 1;

    /**
     * 默认每页展示数目
     */
    public static final int SIZE = 10;

    /**
     * 当前页
     */
    private int current = CURRENT;

    /**
     * 每页显示条数，默认 10
     */
    private int size = SIZE;

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 查询数据列表table返回对象
     */
    private List<T> dataList = Collections.emptyList();

}
