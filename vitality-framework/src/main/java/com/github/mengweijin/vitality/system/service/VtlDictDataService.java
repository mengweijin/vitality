package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlDictDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlDictData;
import com.github.mengweijin.vitality.system.mapper.VtlDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据表 服务类
 *
 * @author mengweijin
 * @since 2023-06-02
 */
@Service
public class VtlDictDataService extends ServiceImpl<VtlDictDataMapper, VtlDictData> {

    @Autowired
    private VtlDictDataMapper vtlDictDataMapper;

    public IPage<VtlDictDataDTO> page(IPage<VtlDictDataDTO> page, VtlDictDataDTO dto){
        return vtlDictDataMapper.page(page, dto);
    }

    public void removeByTypeCode(String typeCode) {
        this.lambdaUpdate().eq(VtlDictData::getTypeCode, typeCode).remove();
    }

    public boolean disabledChange(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlDictData::getDisabled, disabled).eq(VtlDictData::getId, id).update();
    }

    public List<VtlDictData> getByTypeCode(String typeCode) {
        return this.lambdaQuery().eq(VtlDictData::getTypeCode, typeCode).eq(VtlDictData::getDisabled, 0).orderByAsc(VtlDictData::getSeq).list();
    }
}
