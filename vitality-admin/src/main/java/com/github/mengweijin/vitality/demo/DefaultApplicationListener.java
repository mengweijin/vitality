package com.github.mengweijin.vitality.demo;

import com.github.mengweijin.vitality.controller.CommonController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class DefaultApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CommonController commonController;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("{}: ContextRefreshedEvent end.", this.getClass().getSimpleName());
    }
}
