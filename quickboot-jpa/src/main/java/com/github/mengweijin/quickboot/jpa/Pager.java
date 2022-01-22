package com.github.mengweijin.quickboot.jpa;

import com.github.mengweijin.quickboot.framework.domain.AbstractPager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


/**
 * @author mengweijin
 * @date 2021/12/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pager<T> extends AbstractPager<T> {

    public Pager<T> toPager(Page<T> page) {
        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        this.setCurrent(page.getNumber() + 1);
        this.setSize(page.getSize());
        this.setTotal(page.getTotalElements());
        this.setRecords(page.getContent());
        return this;
    }

    public Pageable toPageable(Pager<T> pager) {
        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        long current = pager.getCurrent() <= 0L ? 0L : pager.getCurrent() - 1L;
        return PageRequest.of((int) current, (int) pager.getSize());
    }
}
