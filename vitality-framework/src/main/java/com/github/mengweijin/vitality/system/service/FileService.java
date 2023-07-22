package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.minio.MinioService;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import com.github.mengweijin.vitality.system.dto.FileDTO;
import com.github.mengweijin.vitality.system.entity.FileDO;
import com.github.mengweijin.vitality.system.mapper.FileMapper;
import io.minio.ObjectWriteResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class FileService extends ServiceImpl<FileMapper, FileDO> {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private MinioService minioService;

    public FileDTO detailById(Long id) {
        return fileMapper.detailById(id);
    }

    public IPage<FileDTO> page(IPage<FileDTO> page, FileDTO dto){
        return fileMapper.page(page, dto);
    }

    public List<FileDO> upload(HttpServletRequest request) {
        List<FileDO> fileList = UploadUtils.upload(request, multipartFile -> {
            ObjectWriteResponse response = minioService.upload(multipartFile);
            FileDO file = new FileDO();
            file.setFileName(multipartFile.getOriginalFilename());
            file.setFilePath(response.object());
            file.setBucket(response.bucket());
            return file;
        });
        this.saveBatch(fileList);
        return fileList;
    }

    public String getPreviewUrlById(Long id) {
        FileDO fileDO = this.getById(id);
        return this.getPreviewUrlByFilePath(fileDO.getFilePath());
    }
    public String getPreviewUrlByFilePath(String filePath) {
        return minioService.previewUrl(filePath);
    }
}
