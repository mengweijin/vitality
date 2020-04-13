package com.mengweijin.video.downloader.web.repository;

import com.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
import com.mengweijin.video.downloader.web.entity.Task;
import com.mengweijin.video.downloader.web.enums.TaskStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mengweijin
 */
@Repository
public interface TaskRepository extends BaseJpaRepository<Task, Long> {

    List<Task> findByName(String name);

    List<Task> findByStatus(TaskStatus taskStatus);
}
