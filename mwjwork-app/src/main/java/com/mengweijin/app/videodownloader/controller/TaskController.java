package com.mengweijin.app.videodownloader.controller;

import cn.hutool.core.io.FileUtil;
import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.app.videodownloader.enums.TaskStatus;
import com.mengweijin.app.videodownloader.runner.BaseDownloadRunner;
import com.mengweijin.app.videodownloader.service.TaskService;
import com.mengweijin.mwjwork.framework.util.lambda.LambdaWrapper;
import com.mengweijin.mwjwork.framework.web.BaseController;
import com.mengweijin.mwjwork.jpa.page.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author Meng Wei Jin
 **/
@Validated
@RestController
@RequestMapping("/video-downloader/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<Object> insert(@Valid @RequestBody Task task){
        task.setStatus(TaskStatus.QUEUING);
        taskService.add(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable("id") Long id){
        return taskService.findById(id);
    }

    @GetMapping("/page")
    public Page<Task> findPage(Pager<?> pager, Task task) {
        Sort sort = Sort.by(Sort.Direction.DESC, LambdaWrapper.getFieldName(Task::getCreateTime));
        Pageable pageable = PageRequest.of(pager.getCurrent(), pager.getSize(), sort);
        return taskService.findAll(Example.of(task), pageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         @RequestParam(value = "deleteVideo", defaultValue = "FALSE") boolean deleteVideo) {

        if(deleteVideo) {
            FileUtil.del(BaseDownloadRunner.OUTPUT_PATH + id);
        }
        taskService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
