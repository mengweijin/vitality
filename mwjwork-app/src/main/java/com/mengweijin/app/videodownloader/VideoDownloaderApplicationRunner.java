package com.mengweijin.app.videodownloader;

import com.mengweijin.app.videodownloader.async.AsyncFactory;
import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.app.videodownloader.enums.TaskStatus;
import com.mengweijin.app.videodownloader.runner.DownloadRunnerFactory;
import com.mengweijin.app.videodownloader.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Meng Wei Jin
 * @description 应用启动初始化
 * 注解@Order如果多个自定义ApplicationRunner，用来标明执行顺序，从小到大加载
 **/
@Component
@Order
@Slf4j
public class VideoDownloaderApplicationRunner implements ApplicationRunner {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AsyncFactory asyncFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Task> taskRunningList = taskService.findByStatus(TaskStatus.RUNNING);
        taskRunningList.forEach(task -> {
            Task taskUpdate = new Task();
            taskUpdate.setId(task.getId());
            taskUpdate.setStatus(TaskStatus.QUEUING);
            taskService.update(task.getId(), taskUpdate);
        });

        List<Task> taskQueuingList = taskService.findByStatus(TaskStatus.QUEUING);
        Collections.reverse(taskQueuingList);
        taskQueuingList.forEach(task -> {
            asyncFactory.downloadRunnerTask(DownloadRunnerFactory.getDownLoadRunner(task));
        });
    }

}
