package com.github.mengweijin.mybatisplus.demo.controller;

import com.github.mengweijin.quickboot.framework.domain.FileInfo;
import com.github.mengweijin.quickboot.framework.util.UploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * UserController API
 *
 * @author mengweijin
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    @PostMapping
    public List<FileInfo> upload(HttpServletRequest request) {
        return UploadUtils.upload(request, s -> null);
    }
}
