package com.mengweijin.app.videodownloader.scheduler;

import com.mengweijin.app.videodownloader.async.AsyncFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppScheduler {

    @Autowired
    private AsyncFactory asyncFactory;

    /**
     * 每天晚上4点开启删除任务
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void deleteVideoDownloaderTask() {
        asyncFactory.deleteVideoDownloaderTask();
    }
}
