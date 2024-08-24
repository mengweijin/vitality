package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.domain.entity.LogError;
import com.github.mengweijin.vitality.system.mapper.LogErrorMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  LogError Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogErrorService extends ServiceImpl<LogErrorMapper, LogError> {

    /**
     * Custom paging query
     * @param page page
     * @param logError {@link LogError}
     * @return IPage
     */
    public IPage<LogError> page(IPage<LogError> page, LogError logError){
        LambdaQueryWrapper<LogError> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(logError.getClassName()), LogError::getClassName, logError.getClassName())
                .eq(StrUtil.isNotBlank(logError.getMethodName()), LogError::getMethodName, logError.getMethodName())
                .eq(StrUtil.isNotBlank(logError.getExceptionName()), LogError::getExceptionName, logError.getExceptionName())
                .eq(StrUtil.isNotBlank(logError.getErrorMsg()), LogError::getErrorMsg, logError.getErrorMsg())
                .eq(StrUtil.isNotBlank(logError.getStackTrace()), LogError::getStackTrace, logError.getStackTrace())
                .eq(!Objects.isNull(logError.getId()), LogError::getId, logError.getId())
                .eq(!Objects.isNull(logError.getCreateBy()), LogError::getCreateBy, logError.getCreateBy())
                .eq(!Objects.isNull(logError.getCreateTime()), LogError::getCreateTime, logError.getCreateTime())
                .eq(!Objects.isNull(logError.getUpdateBy()), LogError::getUpdateBy, logError.getUpdateBy())
                .eq(!Objects.isNull(logError.getUpdateTime()), LogError::getUpdateTime, logError.getUpdateTime());
        return this.page(page, query);
    }
}
