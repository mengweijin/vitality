package com.github.mengweijin.quickboot.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Component
public class Pager<T> extends Page<T> {

    private static final long serialVersionUID = -5428777368936766478L;

    /**
     * 默认当前页
     */
    public static final long CURRENT = 1;

    /**
     * 默认每页展示数目
     */
    public static final long SIZE = 10;

    /**
     * 默认总数
     */
    public static final long TOTAL = 0;

    /**
     * 当前页
     */
    private long current = CURRENT;

    /**
     * 每页显示条数，默认 10
     */
    private long size = SIZE;

    /**
     * 总数，当 count 不为 0 时分页插件不会进行 count 查询
     */
    private long total = TOTAL;

    /**
     * 查询数据列表table返回对象
     */
    private List<T> dataList = Collections.emptyList();

    /**
     * 手动分页
     * @param page
     * @param list
     * @param <E>
     * @return
     */
    public static <E> IPage<E> handPage(IPage<E> page, List<E> list){
        if(list == null){
            list = Collections.EMPTY_LIST;
        } else {
            page.setTotal((long)list.size());
        }

        int current = (int)page.getCurrent();
        int size = (int)page.getSize();

        // 截取的开始位置
        int startIndex = (current - 1) * size;
        // 截取的结束位置
        int endIndex = current * size;
        endIndex = list.size() > endIndex ? endIndex : (list.size() % size + startIndex);

        list = list.subList(startIndex, endIndex);
        page.setRecords(list);
        return page;
    }
}
