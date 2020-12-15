package com.github.mengweijin.mybatisplus.demo.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class AsyncFactory {

    @Async
    public Future<String> doTaskInAsync(String name) {
        // do something
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(name + " doTaskIn Async");
        return new AsyncResult<>("SUCCESS " + name);
    }

}
