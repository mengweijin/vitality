package com.mengweijin.mwjwork.framework.jpa.page;

import com.mengweijin.mwjwork.common.constant.Const;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 需要在layui的前台设置请求参数名和返回的数据的参数名根当前类对应
 **/
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Pager<T> extends PageImpl<T> {

    public Pager(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public Pager(List<T> content) {
        super(content);
    }

    public Pager() {
        super(Collections.emptyList());
    }

    /**
     * 默认当前页
     */
    public static final int DEFAULT_PAGE = 1;

    /**
     * 默认每页展示数目
     */
    public static final int DEFAULT_LIMIT = 10;

    /**
     * 默认数据结果总数
     */
    public static final long DEFAULT_COUNT = 0;

    /**
     * 常数变量名 layui当前页
     */
    public static final String VAR_PAGE = "page";

    /**
     * 常数变量名 layui每页展示数目
     */
    public static final String VAR_LIMIT = "limit";

    /**
     * 常数变量名 layui数据结果总数
     */
    public static final String VAR_COUNT = "count";

    /**
     * 当前页
     */
    private int page = DEFAULT_PAGE;

    /**
     * 每页显示条数，默认 10
     */
    private int limit = DEFAULT_LIMIT;

    /**
     * 总数
     */
    private long count = DEFAULT_COUNT;

    /**
     * 查询数据列表 Layui table返回对象
     */
    private List<T> data = Collections.emptyList();

    /**
     * 成功的状态码，默认 0 为成功
     */
    private int code = 0;

    /**
     * 状态信息
     */
    private String msg = Const.EMPTY;

}
