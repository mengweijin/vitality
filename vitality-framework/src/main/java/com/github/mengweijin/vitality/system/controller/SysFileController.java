package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.system.entity.VtlFile;
import com.github.mengweijin.vitality.framework.exception.MinioServiceException;
import com.github.mengweijin.vitality.framework.minio.MinioService;
import com.github.mengweijin.vitality.framework.util.DownLoadUtils;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import io.minio.GetObjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/sys-file")
@Validated
public class SysFileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public List<String> upload(HttpServletRequest request) {
        List<String> list = UploadUtils.upload(request, multipartFile -> minioService.upload(multipartFile));
        // 保存到数据库
        return list;
    }

    /**
     * @param fileId 2022/01/01/test.png
     */
    @PostMapping("/download/{fileId}")
    public void download(@PathVariable("fileId") String fileId, HttpServletRequest request, HttpServletResponse response) {
        VtlFile vtlFile = new VtlFile();
        try(GetObjectResponse getObjectResponse = minioService.download(vtlFile.getFilePath())) {
            DownLoadUtils.download(getObjectResponse, vtlFile.getFileName(), request, response);
        } catch (IOException e) {
            throw new MinioServiceException(e);
        }
    }
}
