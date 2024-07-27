package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.dto.ConfigDTO;
import com.github.mengweijin.system.entity.ConfigDO;
import com.github.mengweijin.system.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@Service
public class ConfigService extends ServiceImpl<ConfigMapper, ConfigDO> {

    @Autowired
    private ConfigMapper configMapper;

    public ConfigDTO detailById(Long id) {
        return configMapper.detailById(id);
    }

    public IPage<ConfigDTO> page(IPage<ConfigDTO> page, ConfigDTO dto){
        return configMapper.page(page, dto);
    }

    public ConfigDO getByCode(String code) {
        return this.lambdaQuery().eq(ConfigDO::getCode, code).one();
    }
}
