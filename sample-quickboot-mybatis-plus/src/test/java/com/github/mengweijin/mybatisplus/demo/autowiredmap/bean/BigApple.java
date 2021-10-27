package com.github.mengweijin.mybatisplus.demo.autowiredmap.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(BigApple.BEAN_NAME)
public class BigApple implements Apple {

    public static final String BEAN_NAME = "bigApple";

    @Override
    public void execute() {
        log.debug("execute {}", this.getClass().getSimpleName());
    }

}
