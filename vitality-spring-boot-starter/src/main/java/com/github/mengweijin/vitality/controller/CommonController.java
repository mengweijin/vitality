package com.github.mengweijin.vitality.controller;

import com.github.mengweijin.vitality.util.DownLoadUtils;
import com.github.mengweijin.vitality.util.UploadUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Validated
@RestController
@RequestMapping("/common")
public class CommonController {

    @PostMapping("/upload")
    public List<String> upload(HttpServletRequest request) {
        return UploadUtils.upload(request);
    }

    //@PostMapping("/download")
    public void download(String path, HttpServletRequest request, HttpServletResponse response) {
        DownLoadUtils.download(path, request, response);
    }
}
