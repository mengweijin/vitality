package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.Config;
import com.github.mengweijin.system.mapper.ConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  Config Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class ConfigService extends ServiceImpl<ConfigMapper, Config> {

    /**
     * Custom paging query
     * @param page page
     * @param config {@link Config}
     * @return IPage
     */
    public IPage<Config> page(IPage<Config> page, Config config){
        LambdaQueryWrapper<Config> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(config.getName()), Config::getName, config.getName())
                .eq(StrUtil.isNotBlank(config.getCode()), Config::getCode, config.getCode())
                .eq(StrUtil.isNotBlank(config.getVal()), Config::getVal, config.getVal())
                .eq(StrUtil.isNotBlank(config.getRemark()), Config::getRemark, config.getRemark())
                .eq(!Objects.isNull(config.getId()), Config::getId, config.getId())
                .eq(!Objects.isNull(config.getCreateBy()), Config::getCreateBy, config.getCreateBy())
                .eq(!Objects.isNull(config.getCreateTime()), Config::getCreateTime, config.getCreateTime())
                .eq(!Objects.isNull(config.getUpdateBy()), Config::getUpdateBy, config.getUpdateBy())
                .eq(!Objects.isNull(config.getUpdateTime()), Config::getUpdateTime, config.getUpdateTime());
        return this.page(page, query);
    }
}
