package com.github.mengweijin.quickboot.demo.async;

import com.github.mengweijin.quickboot.demo.entity.User;
import com.github.mengweijin.quickboot.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class AsyncFactory {
    @Autowired
    private UserService userService;
    @Async
    public Future<String> doTaskInAsync(String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug(name + " doTaskIn Async");
        List<User> list = userService.list();
        list.stream().forEach(System.out::println);
        return new AsyncResult<>("SUCCESS " + name);
    }

}
