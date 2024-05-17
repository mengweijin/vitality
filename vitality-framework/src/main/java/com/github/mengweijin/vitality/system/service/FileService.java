package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import com.github.mengweijin.vitality.system.dto.FileDTO;
import com.github.mengweijin.vitality.system.entity.FileDO;
import com.github.mengweijin.vitality.system.mapper.FileMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Service
public class FileService extends ServiceImpl<FileMapper, FileDO> {

    private final String FORMAT = File.separatorChar + "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd" + File.separatorChar;

    @Autowired
    private FileMapper fileMapper;

    public FileDTO detailById(Long id) {
        return fileMapper.detailById(id);
    }

    public IPage<FileDTO> page(IPage<FileDTO> page, FileDTO dto){
        return fileMapper.page(page, dto);
    }

    public List<FileDO> upload(HttpServletRequest request) {
        List<FileDO> fileList = UploadUtils.upload(request, multipartFile -> {
            String fileName = multipartFile.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(fileName);
            String path = Const.PROJECT_PATH + "uploads" + TimeUtil.format(LocalDate.now(), FORMAT) + IdUtil.simpleUUID() + Const.DOT + suffix;
            UploadUtils.storageFile(multipartFile, path);
            FileDO file = new FileDO();
            file.setFileName(fileName);
            file.setFileSuffix(suffix);
            file.setFilePath(path);
            file.setBucket(null);
            return file;
        });
        this.saveBatch(fileList);
        return fileList;
    }

}
