package com.github.mengweijin.mybatisplus.demo.autowiredmap.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(SmallApple.BEAN_NAME)
public class SmallApple implements Apple {

    public static final String BEAN_NAME = "smallApple";

    @Override
    public void execute() {
        log.debug("execute {}", this.getClass().getSimpleName());
    }

}
