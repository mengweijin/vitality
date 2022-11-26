package com.github.mengweijin.vitality.demo.scheduler;

import com.github.mengweijin.vitality.demo.sys.entity.User;
import com.github.mengweijin.vitality.demo.sys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class SchedulerFactory {

    @Autowired
    private UserService userService;

    //@Scheduled(cron = "*/30 * * * * ?")
    public void doTaskIn30Seconds() {
        // do something
        log.debug("doTaskIn 30 Second");
        List<User> list = userService.list();
        list.stream().forEach(System.out::println);
    }

    //@Scheduled(cron = "0 * * * * ?")
    public void doTaskIn60Second() {
        log.debug("doTaskIn 60 Second");
    }
}
