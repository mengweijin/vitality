package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlLogErrorDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogError;
import com.github.mengweijin.vitality.system.mapper.VtlLogErrorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统错误日志记录表 服务类
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Service
public class VtlLogErrorService extends ServiceImpl<VtlLogErrorMapper, VtlLogError> {

    @Autowired
    private VtlLogErrorMapper vtlLogErrorMapper;

    public VtlLogErrorDTO detailById(Long id) {
        return vtlLogErrorMapper.detailById(id);
    }

    public IPage<VtlLogErrorDTO> page(IPage<VtlLogErrorDTO> page, VtlLogErrorDTO dto){
        return vtlLogErrorMapper.page(page, dto);
    }
}
