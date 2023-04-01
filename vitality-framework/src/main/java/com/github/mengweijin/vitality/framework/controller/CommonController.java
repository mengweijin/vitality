package com.github.mengweijin.vitality.framework.controller;

import com.github.mengweijin.vitality.framework.util.DownLoadUtils;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/common")
@Validated
public class CommonController {

    @PostMapping("/upload")
    public List<String> upload(HttpServletRequest request) {
        return UploadUtils.upload(request);
    }

    /**
     * @param path 基于上传路径。例如：2022/01/01/test.png 实际路径为：UploadUtils.UPLOAD_ROOT_PATH/2022/01/01/test.png
     */
    @PostMapping("/download")
    public void download(String path, HttpServletRequest request, HttpServletResponse response) {
        DownLoadUtils.download(UploadUtils.UPLOAD_ROOT_PATH + path, request, response);
    }
}
