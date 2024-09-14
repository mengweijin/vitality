package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vitality.system.domain.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Dept Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * select parent ids with current id
     *
     * @param id current id
     * @return parent ids with current id
     */
    List<Long> selectParentIdsWithById(Long id);

    /**
     * select children ids
     *
     * @param id current id
     * @return children ids
     */
    List<Long> selectChildrenIdsById(Long id);

    Dept selectByUserId(Long userId);
}

