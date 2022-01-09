package com.github.mengweijin.quickboot.mybatis;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.quickboot.framework.domain.Pager;

/**
 * @author mengweijin
 * @date 2021/12/26
 */
public class PagerConverter {

    public static <T> Pager<T> toPager(IPage<T> page){
        return new Pager<T>()
                .setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setRecords(page.getRecords());
    }

    public static <T> IPage<T> toPage(Pager<T> pager) {
        return new Page<T>().
                setCurrent(pager.getCurrent())
                .setSize(pager.getSize())
                .setTotal(pager.getTotal())
                .setRecords(pager.getRecords());
    }
}
