package com.github.mengweijin.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.system.domain.entity.Dept;
import com.github.mengweijin.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * <p>
 *  Dept Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {

    /**
     * Custom paging query
     * @param page page
     * @param dept {@link Dept}
     * @return IPage
     */
    public IPage<Dept> page(IPage<Dept> page, Dept dept){
        LambdaQueryWrapper<Dept> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(dept.getParentId()), Dept::getParentId, dept.getParentId())
                .eq(StrUtil.isNotBlank(dept.getName()), Dept::getName, dept.getName())
                .eq(StrUtil.isNotBlank(dept.getCode()), Dept::getCode, dept.getCode())
                .eq(!Objects.isNull(dept.getSeq()), Dept::getSeq, dept.getSeq())
                .eq(StrUtil.isNotBlank(dept.getDisabled()), Dept::getDisabled, dept.getDisabled())
                .eq(StrUtil.isNotBlank(dept.getRemark()), Dept::getRemark, dept.getRemark())
                .eq(!Objects.isNull(dept.getId()), Dept::getId, dept.getId())
                .eq(!Objects.isNull(dept.getCreateBy()), Dept::getCreateBy, dept.getCreateBy())
                .eq(!Objects.isNull(dept.getCreateTime()), Dept::getCreateTime, dept.getCreateTime())
                .eq(!Objects.isNull(dept.getUpdateBy()), Dept::getUpdateBy, dept.getUpdateBy())
                .eq(!Objects.isNull(dept.getUpdateTime()), Dept::getUpdateTime, dept.getUpdateTime());
        return this.page(page, query);
    }
}
