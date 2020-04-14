package com.mengweijin.app.videodownloader.controller;

import cn.hutool.core.io.FileUtil;
import com.mengweijin.app.videodownloader.entity.Task;
import com.mengweijin.app.videodownloader.service.TaskService;
import com.mengweijin.mwjwork.framework.util.ChunkDownLoadUtils;
import com.mengweijin.mwjwork.framework.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Meng Wei Jin
 **/
@Slf4j
@RestController
@RequestMapping("/video-downloader/download")
public class DownloadController extends BaseController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/player/{taskId}")
    public void player(@PathVariable(value = "taskId") long taskId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Optional<Task> optional = taskService.findById(taskId);
        if (optional.isPresent()) {
            String attachment = optional.get().getAttachmentPath();
            File file = FileUtil.file(attachment);
            ChunkDownLoadUtils.download(file, request, response);
        } else {
            throw new FileNotFoundException();
        }
    }

    @GetMapping("/{taskId}")
    public void download(@PathVariable(value = "taskId") long taskId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Optional<Task> optional = taskService.findById(taskId);
        if (optional.isPresent()) {
            String attachment = optional.get().getAttachmentPath();
            File file = FileUtil.file(attachment);
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + setDownloadHeader(request, file.getName()));
            FileUtils.copyFile(file, response.getOutputStream());
        } else {
            throw new FileNotFoundException();
        }
    }

    public String setDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String encodeFileName = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
            encodeFileName = encodeFileName.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            encodeFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        } else if (agent.contains("Chrome")) {
            // google浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
        } else {
            // 其它浏览器
            encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8.name());
        }
        return encodeFileName;

    }
}
