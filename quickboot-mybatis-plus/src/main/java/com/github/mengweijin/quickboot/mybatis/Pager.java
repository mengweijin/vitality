package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.quickboot.framework.domain.AbstractPager;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mengweijin
 * @date 2021/12/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pager<T> extends AbstractPager<T> {

    public Pager<T> toPager(IPage<T> page) {
        this.setCurrent(page.getCurrent());
        this.setSize(page.getSize());
        this.setTotal(page.getTotal());
        this.setRecords(page.getRecords());
        return this;
    }

    public IPage<T> toPage() {
        return new Page<T>().
            setCurrent(this.getCurrent())
            .setSize(this.getSize())
            .setTotal(this.getTotal())
            .setRecords(this.getRecords());
    }

}
