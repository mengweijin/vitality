package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.exception.MinioServiceException;
import com.github.mengweijin.vitality.framework.minio.MinioService;
import com.github.mengweijin.vitality.framework.util.DownLoadUtils;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import com.github.mengweijin.vitality.system.entity.VtlFile;
import com.github.mengweijin.vitality.system.service.VtlFileService;
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

    @Autowired
    private VtlFileService fileService;

    @PostMapping("/upload")
    public List<String> upload(HttpServletRequest request) {
        List<String> list = UploadUtils.upload(request, multipartFile -> minioService.upload(multipartFile));
        // 保存到数据库
        return list;
    }

    /**
     * @param fileId uuid in table VTL_FILE
     */
    @PostMapping("/download/{fileId}")
    public R download(@PathVariable("fileId") Long fileId, HttpServletRequest request, HttpServletResponse response) {
        VtlFile vtlFile = fileService.getById(fileId);
        if(vtlFile == null) {
            return R.error("No file was found in database! fileId=" + fileId);
        }
        try(GetObjectResponse getObjectResponse = minioService.download(vtlFile.getFilePath())) {
            DownLoadUtils.download(getObjectResponse, vtlFile.getFileName(), request, response);
            return R.success();
        } catch (IOException e) {
            throw new MinioServiceException(e);
        }
    }
}
