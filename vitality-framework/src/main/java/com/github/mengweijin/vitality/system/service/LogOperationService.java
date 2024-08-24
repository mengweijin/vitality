package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.domain.entity.LogOperation;
import com.github.mengweijin.vitality.system.mapper.LogOperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  LogOperation Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogOperationService extends ServiceImpl<LogOperationMapper, LogOperation> {

    /**
     * Custom paging query
     * @param page page
     * @param logOperation {@link LogOperation}
     * @return IPage
     */
    public IPage<LogOperation> page(IPage<LogOperation> page, LogOperation logOperation){
        LambdaQueryWrapper<LogOperation> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(logOperation.getUrl()), LogOperation::getUrl, logOperation.getUrl())
                .eq(StrUtil.isNotBlank(logOperation.getRequestArgs()), LogOperation::getRequestArgs, logOperation.getRequestArgs())
                .eq(StrUtil.isNotBlank(logOperation.getRequestBody()), LogOperation::getRequestBody, logOperation.getRequestBody())
                .eq(StrUtil.isNotBlank(logOperation.getHttpMethod()), LogOperation::getHttpMethod, logOperation.getHttpMethod())
                .eq(StrUtil.isNotBlank(logOperation.getMethodName()), LogOperation::getMethodName, logOperation.getMethodName())
                .eq(StrUtil.isNotBlank(logOperation.getSuccess()), LogOperation::getSuccess, logOperation.getSuccess())
                .eq(StrUtil.isNotBlank(logOperation.getErrorMsg()), LogOperation::getErrorMsg, logOperation.getErrorMsg())
                .eq(!Objects.isNull(logOperation.getId()), LogOperation::getId, logOperation.getId())
                .eq(!Objects.isNull(logOperation.getCreateBy()), LogOperation::getCreateBy, logOperation.getCreateBy())
                .eq(!Objects.isNull(logOperation.getCreateTime()), LogOperation::getCreateTime, logOperation.getCreateTime())
                .eq(!Objects.isNull(logOperation.getUpdateBy()), LogOperation::getUpdateBy, logOperation.getUpdateBy())
                .eq(!Objects.isNull(logOperation.getUpdateTime()), LogOperation::getUpdateTime, logOperation.getUpdateTime());
        return this.page(page, query);
    }
}
