package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.dto.LogOperationDTO;
import com.github.mengweijin.system.entity.LogOperationDO;
import com.github.mengweijin.system.mapper.LogOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统操作日志表 服务类
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Service
public class LogOperationService extends ServiceImpl<LogOperationMapper, LogOperationDO> {

    @Autowired
    private LogOperationMapper logOperationMapper;

    public LogOperationDTO detailById(Long id) {
        return logOperationMapper.detailById(id);
    }

    public IPage<LogOperationDTO> page(IPage<LogOperationDTO> page, LogOperationDTO dto){
        return logOperationMapper.page(page, dto);
    }
}
