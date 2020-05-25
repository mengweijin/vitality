package com.github.mengweijin.app.videodownloader.repository;

import com.github.mengweijin.app.videodownloader.entity.Task;
import com.github.mengweijin.app.videodownloader.enums.TaskStatus;
import com.github.mengweijin.mwjwork.jpa.repository.BaseJpaRepository;
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
