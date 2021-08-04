package com.github.mengweijin.mybatisplus.demo.autowiredmap.component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmallApple implements Apple {

    @Override
    public void execute() {
        log.debug("execute {}", this.getClass().getName());
    }
}
