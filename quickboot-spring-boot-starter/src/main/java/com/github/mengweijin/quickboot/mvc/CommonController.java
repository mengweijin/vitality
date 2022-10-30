package com.github.mengweijin.quickboot.mvc;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.util.DownLoadUtils;
import com.github.mengweijin.quickboot.util.UploadUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@Validated
@RestController
@RequestMapping("/common")
public class CommonController {

    @PostMapping("/upload/{moduleName}")
    public List<String> upload(@NotBlank @Pattern(regexp = "^[a-zA-Z0-9_]{1,30}$") @PathVariable("moduleName") String moduleName, HttpServletRequest request) {
        return UploadUtils.upload(moduleName, request);
    }

    @PostMapping("/upload")
    public List<String> upload(HttpServletRequest request) {
        return UploadUtils.upload(request);
    }

    //@PostMapping("/download")
    public void download(String path, HttpServletRequest request, HttpServletResponse response) {
        DownLoadUtils.download(path, request, response, FileUtil::file);
    }
}
