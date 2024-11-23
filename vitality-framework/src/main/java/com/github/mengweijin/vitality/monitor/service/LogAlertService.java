package com.github.mengweijin.vitality.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.monitor.domain.entity.LogAlert;
import com.github.mengweijin.vitality.monitor.mapper.LogAlertMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * LogError Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogAlertService extends ServiceImpl<LogAlertMapper, LogAlert> {

    /**
     * Custom paging query
     *
     * @param page     page
     * @param logAlert {@link LogAlert}
     * @return IPage
     */
    public IPage<LogAlert> page(IPage<LogAlert> page, LogAlert logAlert) {
        LambdaQueryWrapper<LogAlert> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(logAlert.getClassName()), LogAlert::getClassName, logAlert.getClassName())
                .eq(StrUtil.isNotBlank(logAlert.getMethodName()), LogAlert::getMethodName, logAlert.getMethodName())
                .eq(StrUtil.isNotBlank(logAlert.getMessage()), LogAlert::getMessage, logAlert.getMessage())
                .eq(StrUtil.isNotBlank(logAlert.getStackTrace()), LogAlert::getStackTrace, logAlert.getStackTrace())
                .eq(!Objects.isNull(logAlert.getId()), LogAlert::getId, logAlert.getId())
                .eq(!Objects.isNull(logAlert.getCreateBy()), LogAlert::getCreateBy, logAlert.getCreateBy())
                .eq(!Objects.isNull(logAlert.getCreateTime()), LogAlert::getCreateTime, logAlert.getCreateTime())
                .eq(!Objects.isNull(logAlert.getUpdateBy()), LogAlert::getUpdateBy, logAlert.getUpdateBy())
                .eq(!Objects.isNull(logAlert.getUpdateTime()), LogAlert::getUpdateTime, logAlert.getUpdateTime())
                .like(StrUtil.isNotBlank(logAlert.getExceptionName()), LogAlert::getExceptionName, logAlert.getExceptionName());
        return this.page(page, query);
    }
}
