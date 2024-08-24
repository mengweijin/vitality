package com.github.mengweijin.vitality.framework.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 */
public final class BeanUtils extends org.springframework.beans.BeanUtils {

    private BeanUtils() {}

    public static <T> T copyBean(Object source, Class<T> cls) {
        if (source == null) {
            return null;
        }
        T object;
        try {
            object = cls.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        copyProperties(source, object);
        return object;
    }

    public static <T> List<T> copyList(List<?> source, Class<T> cls) {
        List<T> target = new ArrayList<>();
        if (source != null && !source.isEmpty()){
            for (Object src: source) {
                T object = copyBean(src, cls);
                target.add(object);
            }
        }
        return target;
    }

    public static <T> IPage<T> copyPage(IPage<?> source, Class<T> cls) {
        if(source == null) {
            return new Page<>();
        }
        List<T> list = copyList(source.getRecords(), cls);
        Page<T> target = Page.of(source.getCurrent(), source.getSize(), source.getTotal());
        target.setRecords(list);
        return target;
    }
}
