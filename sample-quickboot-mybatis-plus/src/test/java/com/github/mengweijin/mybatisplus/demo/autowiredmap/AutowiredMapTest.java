package com.github.mengweijin.mybatisplus.demo.autowiredmap;

import com.github.mengweijin.mybatisplus.demo.autowiredmap.bean.Apple;
import com.github.mengweijin.mybatisplus.demo.autowiredmap.bean.BigApple;
import com.github.mengweijin.mybatisplus.demo.autowiredmap.bean.SmallApple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class AutowiredMapTest {

    @Test
    public void test() {
        String basePackages = "com.github.mengweijin.mybatisplus.demo.autowiredmap";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(basePackages);

        AppleService bean = context.getBean(AppleService.class);

        log.debug("******************************************************");
        // 执行全部实现类
        bean.executeAll();
        log.debug("******************************************************");

        // 根据类型调用不同的实现类方法
        bean.execute(BigApple.class);
        bean.execute(SmallApple.class);
        log.debug("******************************************************");

        // 下面这个不会有任何输出，因为 Spring 容器中没有一个名字为 apple 的 bean.
        bean.execute(Apple.class);
        log.debug("******************************************************");

        context.close();
    }
}
