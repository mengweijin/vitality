package com.mengweijin.app.videodownloader.async;

import cn.hutool.core.collection.CollectionUtil;
import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.app.videodownloader.enums.TaskStatus;
import com.mengweijin.app.videodownloader.runner.BaseDownloadRunner;
import com.mengweijin.app.videodownloader.service.TaskService;
import com.mengweijin.mwjwork.common.constant.Const;
import com.mengweijin.mwjwork.common.util.lambda.LambdaWrapper;
import com.mengweijin.mwjwork.framework.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class AsyncFactory {

    @Async("simpleAsync")
    public Future<String> downloadRunnerTask(BaseDownloadRunner baseDownloadRunner) {
        // 修改任为状态为执行中
        Long id = baseDownloadRunner.getTask().getId();

        TaskService taskService = SpringUtils.getBean(TaskService.class);
        Task task = new Task();
        task.setId(id);
        task.setStatus(TaskStatus.RUNNING);
        taskService.update(id, task);

        Task queryTask = new Task();
        queryTask.setUrl(baseDownloadRunner.getTask().getUrl());
        queryTask.setStatus(TaskStatus.SUCCESS);
        List<Task> taskList = taskService.findAll(Example.of(queryTask));

        Task resultTask = new Task();
        resultTask.setId(id);
        if(CollectionUtil.isNotEmpty(taskList)) {
            // 已经有相同url已经成功的任务，直接获取结果
            resultTask.setStatus(TaskStatus.SUCCESS);
            resultTask.setAttachmentPath(taskList.get(0).getAttachmentPath());
            resultTask.setAttachmentName(taskList.get(0).getAttachmentName());
            taskService.update(id, resultTask);
        } else {
            // 执行任务
            try {
                File file = baseDownloadRunner.execute();
                resultTask.setStatus(TaskStatus.SUCCESS);
                resultTask.setAttachmentPath(file.getAbsolutePath());
                resultTask.setAttachmentName(file.getName());
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
                resultTask.setStatus(TaskStatus.FAILED);
                resultTask.setErrorMessage(e.getMessage());
            } finally {
                taskService.update(id, resultTask);
            }
        }
        return new AsyncResult<>(Const.SUCCESS);
    }

    /**
     * Delete video-downloader task
     *
     * @return
     */
    @Async
    public Future<String> deleteVideoDownloaderTask() {
        int maxRecord = 15;
        TaskService taskService = SpringUtils.getBean(TaskService.class);
        long count = taskService.count();
        if (count >= maxRecord) {
            Sort sort = Sort.by(Sort.Direction.ASC, LambdaWrapper.getFieldName(Task::getCreateTime));
            Pageable pageable = PageRequest.of(0, Math.toIntExact(count - maxRecord), sort);
            Page<Task> page = taskService.findAll(pageable);
            page.get().forEach(task -> taskService.deleteById(task.getId()));
        }

        return new AsyncResult<>(Const.SUCCESS);
    }
}
