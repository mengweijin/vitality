package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.entity.DictData;
import com.github.mengweijin.vita.system.domain.entity.DictType;
import com.github.mengweijin.vita.system.mapper.DictTypeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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
@AllArgsConstructor
public class DictTypeService extends CrudRepository<DictTypeMapper, DictType> {

    private DictDataService dictDataService;

    @Override
    public boolean removeByIds(Collection<?> list) {
        for (Object id : list) {
            DictType dictType = this.getById((Long) id);
            List<DictData> dictDataList = dictDataService.getByCode(dictType.getCode());
            if(CollUtil.isNotEmpty(dictDataList)) {
                throw new ClientException("Please remove dict data first in dict type [" + dictType.getName() + "].");
            }
        }
        return super.removeByIds(list);
    }

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
