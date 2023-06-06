package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlLogOperationDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogOperation;
import com.github.mengweijin.vitality.system.mapper.VtlLogOperationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统操作日志表 服务类
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Service
public class VtlLogOperationService extends ServiceImpl<VtlLogOperationMapper, VtlLogOperation> {

    @Autowired
    private VtlLogOperationMapper vtlLogOperationMapper;

    public VtlLogOperationDTO detailById(Long id) {
        return vtlLogOperationMapper.detailById(id);
    }

    public IPage<VtlLogOperationDTO> page(IPage<VtlLogOperationDTO> page, VtlLogOperationDTO dto){
        return vtlLogOperationMapper.page(page, dto);
    }
}
