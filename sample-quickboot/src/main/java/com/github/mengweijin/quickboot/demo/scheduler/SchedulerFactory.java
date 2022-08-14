package com.github.mengweijin.quickboot.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class SchedulerFactory {

    //@Scheduled(cron = "*/30 * * * * ?")
    public void doTaskIn30Seconds() {
        // do something
        log.debug("doTaskIn 30 Second");
    }

    //@Scheduled(cron = "0 * * * * ?")
    public void doTaskIn60Second() {
        log.debug("doTaskIn 60 Second");
    }
}
