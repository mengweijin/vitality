package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vitality.framework.cache.CacheConst;
import com.github.mengweijin.vitality.framework.cache.CacheNames;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.domain.entity.DictData;
import com.github.mengweijin.vitality.system.mapper.DictDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
public class DictDataService extends CrudRepository<DictDataMapper, DictData> {

    /**
     * Custom paging query
     * @param page page
     * @param dictData {@link DictData}
     * @return IPage
     */
    public IPage<DictData> page(IPage<DictData> page, DictData dictData){
        LambdaQueryWrapper<DictData> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(dictData.getCode()), DictData::getCode, dictData.getCode())
                .eq(StrUtil.isNotBlank(dictData.getVal()), DictData::getVal, dictData.getVal())
                .eq(!Objects.isNull(dictData.getSeq()), DictData::getSeq, dictData.getSeq())
                .eq(StrUtil.isNotBlank(dictData.getDisabled()), DictData::getDisabled, dictData.getDisabled())
                .eq(StrUtil.isNotBlank(dictData.getRemark()), DictData::getRemark, dictData.getRemark())
                .eq(!Objects.isNull(dictData.getId()), DictData::getId, dictData.getId())
                .eq(!Objects.isNull(dictData.getCreateBy()), DictData::getCreateBy, dictData.getCreateBy())
                .eq(!Objects.isNull(dictData.getCreateTime()), DictData::getCreateTime, dictData.getCreateTime())
                .eq(!Objects.isNull(dictData.getUpdateBy()), DictData::getUpdateBy, dictData.getUpdateBy())
                .eq(!Objects.isNull(dictData.getUpdateTime()), DictData::getUpdateTime, dictData.getUpdateTime())
                .like(StrUtil.isNotBlank(dictData.getLabel()), DictData::getLabel, dictData.getLabel());
        query.orderByAsc(DictData::getSeq);
        return this.page(page, query);
    }

    @Cacheable(value = CacheNames.DICT_DATA_VAL_TO_LABEL, key = "#code + ':' + #val", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getLabelByCodeAndVal(String code, String val) {
        return this.lambdaQuery()
                .select(DictData::getLabel)
                .eq(DictData::getCode, code)
                .eq(DictData::getVal, val)
                .oneOpt()
                .map(DictData::getLabel)
                .orElse(null);
    }

    public List<DictData> getByCode(String code) {
        return this.lambdaQuery()
                .eq(DictData::getCode, code)
                .orderByAsc(DictData::getSeq)
                .list();
    }

    public void checkValDuplicate(String code, String val) {
        Optional<DictData> optional = this.lambdaQuery().eq(DictData::getCode, code).eq(DictData::getVal, val).oneOpt();
        if (optional.isPresent()) {
            throw new ClientException(StrUtil.format("The dict type code[{}] and value[{}] already exists!", code, val));
        }
    }

}
