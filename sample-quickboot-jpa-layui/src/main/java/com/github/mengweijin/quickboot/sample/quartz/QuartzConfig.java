package com.github.mengweijin.quickboot.sample.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/1/10
 */
@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail myJobDetail(){
        JobDetail jobDetail = JobBuilder.newJob(SampleJob.class)
                .withIdentity("myJob1","MyJobGroup1")
                .usingJobData("job_param","job_param1")
                .storeDurably()
                .build();

        return jobDetail;
    }

    @Bean
    public Trigger myTrigger(){
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(myJobDetail())
                .withIdentity("myTrigger1","myTriggerGroup1")
                .usingJobData("job_trigger_param","job_trigger_param1")
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
                .build();

        return trigger;
    }
}
