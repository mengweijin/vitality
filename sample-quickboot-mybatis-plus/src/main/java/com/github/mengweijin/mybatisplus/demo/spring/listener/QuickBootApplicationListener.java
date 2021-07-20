package com.github.mengweijin.mybatisplus.demo.spring.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class QuickBootApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("{}: ContextRefreshedEvent end.", this.getClass().getSimpleName());
    }
}
