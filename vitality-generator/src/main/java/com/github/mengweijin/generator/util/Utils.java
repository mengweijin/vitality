package com.github.mengweijin.generator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2022/12/24
 */
public class Utils {
    public static Class<?> getSpringBootApplicationClass() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
        return annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass();
    }

    public static String getSpringBootApplicationClassPackage() {
        return ClassUtil.getPackage(getSpringBootApplicationClass());
    }

    public static <E> List<E> copyList(List<?> sourceList, Class<E> cls) {
        List<E> list = new ArrayList<>();
        if(sourceList != null) {
            try {
                for (Object obj : sourceList) {
                    E instance = cls.newInstance();
                    BeanUtils.copyProperties(obj, instance);
                    list.add(instance);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    public static ObjectMapper objectMapper() {
        return SpringUtil.getBean(ObjectMapper.class);
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
