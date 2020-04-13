package com.mengweijin.video.downloader.web.service;

import com.mengweijin.mwjwork.jpa.service.BaseService;
import com.mengweijin.video.downloader.web.entity.Task;
import com.mengweijin.video.downloader.web.enums.TaskStatus;
import com.mengweijin.video.downloader.web.repository.TaskRepository;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
public interface TaskService extends BaseService<Task, Long, TaskRepository> {

    Task add(Task task);

    List<Task> findByStatus(TaskStatus taskStatus);
}
