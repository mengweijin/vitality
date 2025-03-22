package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.VitalityProperties;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.util.UploadUtils;
import com.github.mengweijin.vita.system.domain.entity.Oss;
import com.github.mengweijin.vita.system.mapper.OssMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
@AllArgsConstructor
public class OssService extends CrudRepository<OssMapper, Oss> {

    private VitalityProperties vitalityProperties;

    @Override
    public boolean removeByIds(Collection<?> list) {
        // 注意顺序，先查出来
        List<Oss> ossList = this.lambdaQuery().in(Oss::getId, list).list();

        List<String> shouldBeDeleteFromDistFilePathList = new ArrayList<>();
        ossList.forEach(oss -> {
            if (this.countByMd5(oss.getMd5()) <= 1) {
                shouldBeDeleteFromDistFilePathList.add(oss.getStoragePath());
            }
        });

        boolean removed = super.removeByIds(list);
        if (removed) {
            shouldBeDeleteFromDistFilePathList.forEach(FileUtil::del);
        }
        return removed;
    }

    /**
     * Custom paging query
     * @param page page
     * @param oss {@link Oss}
     * @return IPage
     */
    public IPage<Oss> page(IPage<Oss> page, Oss oss){
        LambdaQueryWrapper<Oss> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(oss.getSuffix()), Oss::getSuffix, oss.getSuffix())
                .eq(StrUtil.isNotBlank(oss.getStoragePath()), Oss::getStoragePath, oss.getStoragePath())
                .eq(StrUtil.isNotBlank(oss.getMd5()), Oss::getMd5, oss.getMd5())
                .eq(!Objects.isNull(oss.getId()), Oss::getId, oss.getId())
                .eq(!Objects.isNull(oss.getCreateBy()), Oss::getCreateBy, oss.getCreateBy())
                .eq(!Objects.isNull(oss.getCreateTime()), Oss::getCreateTime, oss.getCreateTime())
                .eq(!Objects.isNull(oss.getUpdateBy()), Oss::getUpdateBy, oss.getUpdateBy())
                .eq(!Objects.isNull(oss.getUpdateTime()), Oss::getUpdateTime, oss.getUpdateTime())
                .like(StrUtil.isNotBlank(oss.getName()), Oss::getName, oss.getName());
        return this.page(page, query);
    }

    public List<Oss> getByMd5(String md5) {
        return this.lambdaQuery().eq(Oss::getMd5, md5).list();
    }

    public long countByMd5(String md5) {
        return this.lambdaQuery().eq(Oss::getMd5, md5).count();
    }

    public List<Oss> upload(HttpServletRequest request) {
        List<Oss> list = UploadUtils.upload(request, multipartFile -> {
            String md5 = UploadUtils.md5(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(fileName);

            Oss oss = new Oss();
            oss.setMd5(md5);
            oss.setName(fileName);
            oss.setSuffix(suffix);

            List<Oss> ossList = this.getByMd5(md5);
            if (CollUtil.isEmpty(ossList)) {
                String storagePath = getPath(vitalityProperties.getFileDir(), suffix);
                copyFile(multipartFile, storagePath);
                oss.setStoragePath(storagePath);
            } else {
                String storagePath = ossList.get(0).getStoragePath();
                if (!FileUtil.exists(storagePath)) {
                    copyFile(multipartFile, storagePath);
                }
                oss.setStoragePath(storagePath);
            }
            return oss;
        });
        this.saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
        return list;
    }

    public static void copyFile(MultipartFile multipartFile, String path) {
        try {
            FileUtil.copy(multipartFile.getInputStream(), FileUtil.file(path));
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public static String getPath(String dir, String suffix) {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear());
        String month = CharSequenceUtil.padPre(String.valueOf(now.getMonthValue()), 2, "0");
        String day = CharSequenceUtil.padPre(String.valueOf(now.getDayOfMonth()), 2, "0");
        return dir + String.join(File.separator, year, month, day, IdUtil.simpleUUID()) + Const.DOT + suffix;
    }
}
