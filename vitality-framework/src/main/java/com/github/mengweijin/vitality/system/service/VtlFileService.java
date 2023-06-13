package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.minio.MinioService;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import com.github.mengweijin.vitality.system.dto.VtlFileDTO;
import com.github.mengweijin.vitality.system.entity.VtlFile;
import com.github.mengweijin.vitality.system.mapper.VtlFileMapper;
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
public class VtlFileService extends ServiceImpl<VtlFileMapper, VtlFile> {

    @Autowired
    private VtlFileMapper vtlFileMapper;

    @Autowired
    private MinioService minioService;

    public VtlFileDTO detailById(Long id) {
        return vtlFileMapper.detailById(id);
    }

    public IPage<VtlFileDTO> page(IPage<VtlFileDTO> page, VtlFileDTO dto){
        return vtlFileMapper.page(page, dto);
    }

    public List<VtlFile> upload(HttpServletRequest request) {
        List<VtlFile> fileList = UploadUtils.upload(request, multipartFile -> {
            ObjectWriteResponse response = minioService.upload(multipartFile);
            VtlFile file = new VtlFile();
            file.setFileName(multipartFile.getOriginalFilename());
            file.setFilePath(response.object());
            file.setBucket(response.bucket());
            return file;
        });
        this.saveBatch(fileList);
        return fileList;
    }

    public String getPreviewUrlById(Long id) {
        VtlFile vtlFile = this.getById(id);
        return this.getPreviewUrlByFilePath(vtlFile.getFilePath());
    }
    public String getPreviewUrlByFilePath(String filePath) {
        return minioService.previewUrl(filePath);
    }
}
