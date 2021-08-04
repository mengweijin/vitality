package com.github.mengweijin.mybatisplus.demo.autowiredmap;

import com.github.mengweijin.mybatisplus.demo.autowiredmap.component.Apple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AppleService {
    /**
     * Spring 会自动注入所有实现了 Apple 接口的 bean 到这个 appleBeanMap 中，其中 key 为 spring bean 的 name.
     */
    @Autowired
    private Map<String, Apple> appleMap;

    public void executeAll() {
        appleMap.forEach((beanName, apple) -> apple.execute());
    }

    public <T extends Apple> void execute(Class<T> cls) {
        String generatedBeanName = AppleBeanPostProcessor.generateBeanName(cls);
        log.debug("Generated Bean Name = {}", generatedBeanName);
        Apple apple = appleMap.get(generatedBeanName);
        if (apple != null) {
            apple.execute();
        }
    }
}
