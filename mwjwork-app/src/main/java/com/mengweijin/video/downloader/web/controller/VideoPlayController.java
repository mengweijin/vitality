package com.mengweijin.video.downloader.web.controller;

import cn.hutool.core.io.FileUtil;
import com.mengweijin.mwjwork.framework.util.ChunkDownLoadUtils;
import com.mengweijin.mwjwork.framework.web.BaseController;
import com.mengweijin.video.downloader.web.entity.Task;
import com.mengweijin.video.downloader.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Meng Wei Jin
 **/
@Slf4j
@RestController
public class VideoPlayController extends BaseController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/player/{taskId}")
    public void player(@PathVariable(value = "taskId") long taskId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Optional<Task> optional = taskService.findById(taskId);
        if(optional.isPresent()){
            String attachment = optional.get().getAttachment();
            File file = FileUtil.file(attachment);
            ChunkDownLoadUtils.download(file, request, response);
        } else {
            throw new FileNotFoundException();
        }
    }



}
