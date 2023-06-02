package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlDictTypeDTO;
import com.github.mengweijin.vitality.system.entity.VtlDictType;
import com.github.mengweijin.vitality.system.mapper.VtlDictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 字典类型表 服务类
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Service
public class VtlDictTypeService extends ServiceImpl<VtlDictTypeMapper, VtlDictType> {

    @Autowired
    private VtlDictTypeMapper vtlDictTypeMapper;

    @Autowired
    private VtlDictDataService dictDataService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        VtlDictType dictType = this.getById(id);
        dictDataService.removeByTypeCode(dictType.getTypeCode());
        return super.removeById(id);
    }

    public IPage<VtlDictTypeDTO> page(IPage<VtlDictTypeDTO> page, VtlDictTypeDTO dto){
        return vtlDictTypeMapper.page(page, dto);
    }
}
