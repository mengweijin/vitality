package com.mengweijin.video.downloader.web.service.impl;

import com.mengweijin.mwjwork.jpa.service.BaseServiceImpl;
import com.mengweijin.video.downloader.runner.DownloadRunnerFactory;
import com.mengweijin.video.downloader.web.async.AsyncFactory;
import com.mengweijin.video.downloader.web.entity.Task;
import com.mengweijin.video.downloader.web.enums.TaskStatus;
import com.mengweijin.video.downloader.web.repository.TaskRepository;
import com.mengweijin.video.downloader.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Meng Wei Jin
 **/
@Transactional(rollbackFor = Exception.class)
@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, Long, TaskRepository> implements TaskService {

    @Autowired
    private AsyncFactory asyncFactory;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task add(Task task) {
        task = this.saveAndFlush(task);
        asyncFactory.downloadRunnerTask(DownloadRunnerFactory.getDownLoadRunner(task));
        return task;
    }

    @Override
    public List<Task> findByStatus(TaskStatus taskStatus) {
        List<Task> taskList = taskRepository.findByStatus(taskStatus);
        taskList.sort((o1, o2) -> o1.getCreateTime().isBefore(o2.getCreateTime()) ? 1: -1);
        return taskList;
    }
}
