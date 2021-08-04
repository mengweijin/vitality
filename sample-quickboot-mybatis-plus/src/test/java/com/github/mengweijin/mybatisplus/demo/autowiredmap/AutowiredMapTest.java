package com.github.mengweijin.mybatisplus.demo.autowiredmap;

import com.github.mengweijin.mybatisplus.demo.autowiredmap.component.Apple;
import com.github.mengweijin.mybatisplus.demo.autowiredmap.component.BigApple;
import com.github.mengweijin.mybatisplus.demo.autowiredmap.component.SmallApple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.github.mengweijin.mybatisplus.demo.autowiredmap")
public class AutowiredMapTest implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutowiredMapTest.class, args);
        AppleService bean = applicationContext.getBean(AppleService.class);

        // 执行全部实现类
        bean.executeAll();
        log.debug("******************************************************");

        // 下面几个会执行对应的方法
        bean.execute(BigApple.class);
        ;
        bean.execute(SmallApple.class);
        log.debug("******************************************************");

        // 下面这个不会有任何输出，因为 Spring 容器中没有一个名字为 apple 的 bean.
        bean.execute(Apple.class);

        context.close();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
