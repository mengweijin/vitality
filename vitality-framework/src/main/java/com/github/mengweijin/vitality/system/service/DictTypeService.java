package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.DictTypeDTO;
import com.github.mengweijin.vitality.system.entity.DictTypeDO;
import com.github.mengweijin.vitality.system.mapper.DictTypeMapper;
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
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictTypeDO> {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Autowired
    private DictDataService dictDataService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        DictTypeDO dictType = this.getById(id);
        dictDataService.removeByTypeCode(dictType.getTypeCode());
        return super.removeById(id);
    }

    public IPage<DictTypeDTO> page(IPage<DictTypeDTO> page, DictTypeDTO dto){
        return dictTypeMapper.page(page, dto);
    }
}
