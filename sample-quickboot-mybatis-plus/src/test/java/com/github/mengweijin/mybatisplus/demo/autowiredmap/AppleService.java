package com.github.mengweijin.mybatisplus.demo.autowiredmap;

import com.github.mengweijin.mybatisplus.demo.autowiredmap.bean.Apple;
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

    /**
     * 执行所有实现类的方法
     */
    public void executeAll() {
        appleMap.forEach((beanName, apple) -> apple.execute());
    }

    /**
     * 根据类型调用不同的实现类方法
     *
     * @param beanName
     */
    public void execute(String beanName) {
        appleMap.get(beanName).execute();
    }
}
