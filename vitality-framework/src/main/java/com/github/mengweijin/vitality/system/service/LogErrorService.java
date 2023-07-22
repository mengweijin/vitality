package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.LogErrorDTO;
import com.github.mengweijin.vitality.system.entity.LogErrorDO;
import com.github.mengweijin.vitality.system.mapper.LogErrorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统错误日志记录表 服务类
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Service
public class LogErrorService extends ServiceImpl<LogErrorMapper, LogErrorDO> {

    @Autowired
    private LogErrorMapper logErrorMapper;

    public LogErrorDTO detailById(Long id) {
        return logErrorMapper.detailById(id);
    }

    public IPage<LogErrorDTO> page(IPage<LogErrorDTO> page, LogErrorDTO dto){
        return logErrorMapper.page(page, dto);
    }
}
