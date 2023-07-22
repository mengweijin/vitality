package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.DictDataDTO;
import com.github.mengweijin.vitality.system.entity.DictDataDO;
import com.github.mengweijin.vitality.system.mapper.DictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典数据表 服务类
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Service
public class DictDataService extends ServiceImpl<DictDataMapper, DictDataDO> {

    @Autowired
    private DictDataMapper dictDataMapper;

    public IPage<DictDataDTO> page(IPage<DictDataDTO> page, DictDataDTO dto){
        return dictDataMapper.page(page, dto);
    }

    public void removeByTypeCode(String typeCode) {
        this.lambdaUpdate().eq(DictDataDO::getTypeCode, typeCode).remove();
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(DictDataDO::getDisabled, disabled).eq(DictDataDO::getId, id).update();
    }

    public List<DictDataDO> getByTypeCode(String typeCode) {
        return this.lambdaQuery().eq(DictDataDO::getTypeCode, typeCode).eq(DictDataDO::getDisabled, 0).orderByAsc(DictDataDO::getSeq).list();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public boolean setDefaultSelected(Long id) {
        DictDataDO dictDataDO = this.getById(id);
        this.lambdaUpdate().set(DictDataDO::getDefaultSelected, 1).eq(DictDataDO::getTypeCode, dictDataDO.getTypeCode()).eq(DictDataDO::getId, id).update();
        return this.lambdaUpdate().set(DictDataDO::getDefaultSelected, 0).eq(DictDataDO::getTypeCode, dictDataDO.getTypeCode()).ne(DictDataDO::getId, id).update();
    }
}
