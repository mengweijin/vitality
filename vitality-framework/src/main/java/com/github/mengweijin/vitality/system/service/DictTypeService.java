package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.system.domain.entity.DictType;
import com.github.mengweijin.vitality.system.mapper.DictTypeMapper;
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
public class DictTypeService extends CrudRepository<DictTypeMapper, DictType> {

    /**
     * Custom paging query
     * @param page page
     * @param dictType {@link DictType}
     * @return IPage
     */
    public IPage<DictType> page(IPage<DictType> page, DictType dictType){
        LambdaQueryWrapper<DictType> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(dictType.getRemark()), DictType::getRemark, dictType.getRemark())
                .eq(!Objects.isNull(dictType.getId()), DictType::getId, dictType.getId())
                .eq(!Objects.isNull(dictType.getCreateBy()), DictType::getCreateBy, dictType.getCreateBy())
                .eq(!Objects.isNull(dictType.getCreateTime()), DictType::getCreateTime, dictType.getCreateTime())
                .eq(!Objects.isNull(dictType.getUpdateBy()), DictType::getUpdateBy, dictType.getUpdateBy())
                .eq(!Objects.isNull(dictType.getUpdateTime()), DictType::getUpdateTime, dictType.getUpdateTime())
                .like(StrUtil.isNotBlank(dictType.getName()), DictType::getName, dictType.getName())
                .like(StrUtil.isNotBlank(dictType.getCode()), DictType::getCode, dictType.getCode());
        return this.page(page, query);
    }

    public DictType getByCode(String code) {
        return this.lambdaQuery().eq(DictType::getCode, code).one();
    }
}
