package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.File;
import com.github.mengweijin.system.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  File Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class FileService extends ServiceImpl<FileMapper, File> {

    /**
     * Custom paging query
     * @param page page
     * @param file {@link File}
     * @return IPage
     */
    public IPage<File> page(IPage<File> page, File file){
        LambdaQueryWrapper<File> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(file.getName()), File::getName, file.getName())
                .eq(StrUtil.isNotBlank(file.getSuffix()), File::getSuffix, file.getSuffix())
                .eq(StrUtil.isNotBlank(file.getStoragePath()), File::getStoragePath, file.getStoragePath())
                .eq(!Objects.isNull(file.getId()), File::getId, file.getId())
                .eq(!Objects.isNull(file.getCreateBy()), File::getCreateBy, file.getCreateBy())
                .eq(!Objects.isNull(file.getCreateTime()), File::getCreateTime, file.getCreateTime())
                .eq(!Objects.isNull(file.getUpdateBy()), File::getUpdateBy, file.getUpdateBy())
                .eq(!Objects.isNull(file.getUpdateTime()), File::getUpdateTime, file.getUpdateTime());
        return this.page(page, query);
    }
}
