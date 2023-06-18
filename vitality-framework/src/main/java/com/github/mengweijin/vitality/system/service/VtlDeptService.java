package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
import com.github.mengweijin.vitality.system.mapper.VtlDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 部门管理表 服务类
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@Service
public class VtlDeptService extends ServiceImpl<VtlDeptMapper, VtlDept> {

    @Autowired
    private VtlDeptMapper vtlDeptMapper;

    public VtlDeptDTO detailById(Long id) {
        return vtlDeptMapper.detailById(id);
    }

    public IPage<VtlDeptDTO> page(IPage<VtlDeptDTO> page, VtlDeptDTO dto){
        return vtlDeptMapper.page(page, dto);
    }
}
