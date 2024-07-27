package com.github.mengweijin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.util.DownLoadUtils;
import com.github.mengweijin.system.dto.FileDTO;
import com.github.mengweijin.system.entity.FileDO;
import com.github.mengweijin.system.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/vtl-file")
@Validated
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public List<FileDO> upload(HttpServletRequest request) {
        return fileService.upload(request);
    }

    /**
     * @param fileId uuid in table VTL_FILE
     */
    @GetMapping("/download/{fileId}")
    public R<Void> download(@PathVariable("fileId") Long fileId, HttpServletRequest request, HttpServletResponse response) {
        FileDO fileDO = fileService.getById(fileId);
        if(fileDO == null) {
            return R.failure("No file was found in database! fileId=" + fileId);
        }
        try(FileInputStream in = new FileInputStream(fileDO.getFilePath())) {
            DownLoadUtils.download(in, fileDO.getFileName(), request, response);
            return R.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/detail/{id}")
    public FileDTO detailById(@PathVariable("id") Long id) {
        return fileService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<FileDTO> page(Page<FileDTO> page, FileDTO dto) {
        return fileService.page(page, dto);
    }

}
