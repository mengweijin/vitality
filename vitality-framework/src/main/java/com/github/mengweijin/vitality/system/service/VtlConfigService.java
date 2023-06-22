package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlConfigDTO;
import com.github.mengweijin.vitality.system.entity.VtlConfig;
import com.github.mengweijin.vitality.system.mapper.VtlConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 配置管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-04
 */
@Service
public class VtlConfigService extends ServiceImpl<VtlConfigMapper, VtlConfig> {

    @Autowired
    private VtlConfigMapper vtlConfigMapper;

    public VtlConfigDTO detailById(Long id) {
        return vtlConfigMapper.detailById(id);
    }

    public IPage<VtlConfigDTO> page(IPage<VtlConfigDTO> page, VtlConfigDTO dto){
        return vtlConfigMapper.page(page, dto);
    }

    public VtlConfig getByCode(String code) {
        return this.lambdaQuery().eq(VtlConfig::getCode, code).one();
    }
}
