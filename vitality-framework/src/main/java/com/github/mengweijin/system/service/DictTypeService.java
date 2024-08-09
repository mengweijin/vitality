package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.DictType;
import com.github.mengweijin.system.mapper.DictTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  DictType Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

    /**
     * Custom paging query
     * @param page page
     * @param dictType {@link DictType}
     * @return IPage
     */
    public IPage<DictType> page(IPage<DictType> page, DictType dictType){
        LambdaQueryWrapper<DictType> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(dictType.getName()), DictType::getName, dictType.getName())
                .eq(StrUtil.isNotBlank(dictType.getTypeCode()), DictType::getTypeCode, dictType.getTypeCode())
                .eq(StrUtil.isNotBlank(dictType.getRemark()), DictType::getRemark, dictType.getRemark())
                .eq(!Objects.isNull(dictType.getId()), DictType::getId, dictType.getId())
                .eq(!Objects.isNull(dictType.getCreateBy()), DictType::getCreateBy, dictType.getCreateBy())
                .eq(!Objects.isNull(dictType.getCreateTime()), DictType::getCreateTime, dictType.getCreateTime())
                .eq(!Objects.isNull(dictType.getUpdateBy()), DictType::getUpdateBy, dictType.getUpdateBy())
                .eq(!Objects.isNull(dictType.getUpdateTime()), DictType::getUpdateTime, dictType.getUpdateTime());
        return this.page(page, query);
    }
}
