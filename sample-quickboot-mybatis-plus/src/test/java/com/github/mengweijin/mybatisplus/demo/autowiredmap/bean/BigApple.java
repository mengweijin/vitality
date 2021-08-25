package com.github.mengweijin.mybatisplus.demo.autowiredmap.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BigApple implements Apple {

    @Override
    public void execute() {
        log.debug("execute {}", this.getClass().getSimpleName());
    }
}
