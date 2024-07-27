package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.framework.constant.Const;
import com.github.mengweijin.framework.util.UploadUtils;
import com.github.mengweijin.system.dto.FileDTO;
import com.github.mengweijin.system.entity.FileDO;
import com.github.mengweijin.system.mapper.FileMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.DatePattern;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.core.text.StrUtil;
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

    public static final String UPLOAD_DIR = Const.PROJECT_DIR + "uploads";

    private final String FORMAT = StrUtil.replace(DatePattern.NORM_DATE_PATTERN, Const.DASH, File.separator);

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
            String path = UPLOAD_DIR + TimeUtil.format(LocalDate.now(), FORMAT) + File.separator + IdUtil.simpleUUID() + Const.DOT + suffix;
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
