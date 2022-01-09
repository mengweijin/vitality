package com.github.mengweijin.quickboot.jpa;

import com.github.mengweijin.quickboot.framework.domain.Pager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author mengweijin
 * @date 2021/12/26
 */
public class PagerConverter {

    public static <T> Pager<T> toPager(Page<T> page){
        return new Pager<T>()
                // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
                .setCurrent(page.getNumber() + 1)
                .setSize(page.getSize())
                .setTotal(page.getTotalElements())
                .setRecords(page.getContent());
    }

    public static <T> Pageable toPageable(Pager<T> pager) {
        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        long current = pager.getCurrent() <= 0L ? 0L : pager.getCurrent() - 1L;
        return PageRequest.of((int) current, (int) pager.getSize());
    }
}
