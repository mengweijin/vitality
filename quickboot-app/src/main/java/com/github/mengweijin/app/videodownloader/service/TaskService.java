package com.github.mengweijin.app.videodownloader.service;

import com.github.mengweijin.app.videodownloader.entity.Task;
import com.github.mengweijin.app.videodownloader.enums.TaskStatus;
import com.github.mengweijin.app.videodownloader.repository.TaskRepository;
import com.github.mengweijin.mwjwork.jpa.service.BaseService;

import java.util.List;

/**
 * @author Meng Wei Jin
 * @description
 **/
public interface TaskService extends BaseService<Task, Long, TaskRepository> {

    Task add(Task task);

    List<Task> findByStatus(TaskStatus taskStatus);
}
