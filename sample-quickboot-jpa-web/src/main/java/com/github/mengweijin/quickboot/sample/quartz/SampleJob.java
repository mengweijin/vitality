package com.github.mengweijin.quickboot.sample.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * @author mengweijin
 * @date 2022/1/10
 */
@Slf4j
public class SampleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Quartz job starting in " + LocalDateTime.now());
    }
}
