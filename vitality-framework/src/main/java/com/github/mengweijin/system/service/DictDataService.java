package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.DictData;
import com.github.mengweijin.system.mapper.DictDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  DictData Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DictDataService extends ServiceImpl<DictDataMapper, DictData> {

    /**
     * Custom paging query
     * @param page page
     * @param dictData {@link DictData}
     * @return IPage
     */
    public IPage<DictData> page(IPage<DictData> page, DictData dictData){
        LambdaQueryWrapper<DictData> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(dictData.getTypeCode()), DictData::getTypeCode, dictData.getTypeCode())
                .eq(StrUtil.isNotBlank(dictData.getDataCode()), DictData::getDataCode, dictData.getDataCode())
                .eq(StrUtil.isNotBlank(dictData.getLabel()), DictData::getLabel, dictData.getLabel())
                .eq(!Objects.isNull(dictData.getSeq()), DictData::getSeq, dictData.getSeq())
                .eq(StrUtil.isNotBlank(dictData.getSelected()), DictData::getSelected, dictData.getSelected())
                .eq(StrUtil.isNotBlank(dictData.getDisabled()), DictData::getDisabled, dictData.getDisabled())
                .eq(StrUtil.isNotBlank(dictData.getRemark()), DictData::getRemark, dictData.getRemark())
                .eq(!Objects.isNull(dictData.getId()), DictData::getId, dictData.getId())
                .eq(!Objects.isNull(dictData.getCreateBy()), DictData::getCreateBy, dictData.getCreateBy())
                .eq(!Objects.isNull(dictData.getCreateTime()), DictData::getCreateTime, dictData.getCreateTime())
                .eq(!Objects.isNull(dictData.getUpdateBy()), DictData::getUpdateBy, dictData.getUpdateBy())
                .eq(!Objects.isNull(dictData.getUpdateTime()), DictData::getUpdateTime, dictData.getUpdateTime());
        return this.page(page, query);
    }

    public List<DictData> getByTypeCode(String typeCode) {
        return this.lambdaQuery().eq(DictData::getTypeCode, typeCode).list();
    }
}
