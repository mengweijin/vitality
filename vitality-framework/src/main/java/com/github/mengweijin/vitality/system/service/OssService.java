package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.framework.constant.Const;
import com.github.mengweijin.vitality.framework.util.UploadUtils;
import com.github.mengweijin.vitality.system.domain.entity.Oss;
import com.github.mengweijin.vitality.system.mapper.OssMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.DatePattern;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
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
public class OssService extends CrudRepository<OssMapper, Oss> {

    public static final String STORAGE_DIR = Const.PROJECT_DIR + "oss" + File.separatorChar;

    private static final String FORMAT = DatePattern.NORM_DATE_PATTERN.replaceAll(Const.DASH, Const.SLASH);

    /**
     * Custom paging query
     * @param page page
     * @param oss {@link Oss}
     * @return IPage
     */
    public IPage<Oss> page(IPage<Oss> page, Oss oss){
        LambdaQueryWrapper<Oss> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(oss.getName()), Oss::getName, oss.getName())
                .eq(StrUtil.isNotBlank(oss.getSuffix()), Oss::getSuffix, oss.getSuffix())
                .eq(StrUtil.isNotBlank(oss.getStoragePath()), Oss::getStoragePath, oss.getStoragePath())
                .eq(!Objects.isNull(oss.getId()), Oss::getId, oss.getId())
                .eq(!Objects.isNull(oss.getCreateBy()), Oss::getCreateBy, oss.getCreateBy())
                .eq(!Objects.isNull(oss.getCreateTime()), Oss::getCreateTime, oss.getCreateTime())
                .eq(!Objects.isNull(oss.getUpdateBy()), Oss::getUpdateBy, oss.getUpdateBy())
                .eq(!Objects.isNull(oss.getUpdateTime()), Oss::getUpdateTime, oss.getUpdateTime());
        return this.page(page, query);
    }

    public List<Oss> upload(HttpServletRequest request) {
        List<Oss> list = UploadUtils.upload(request, multipartFile -> {
            String fileName = multipartFile.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(fileName);
            String path = STORAGE_DIR + TimeUtil.format(LocalDate.now(), FORMAT) + File.separatorChar + IdUtil.simpleUUID() + Const.DOT + suffix;
            UploadUtils.storageFile(multipartFile, path);
            Oss oss = new Oss();
            oss.setName(fileName);
            oss.setSuffix(suffix);
            oss.setStoragePath(path);
            return oss;
        });
        this.saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
        return list;
    }
}
