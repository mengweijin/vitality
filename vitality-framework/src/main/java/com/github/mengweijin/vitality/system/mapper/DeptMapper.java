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
     * select children ids
     *
     * @param id current id
     * @return children ids
     */
    List<Long> selectChildrenIdsById(Long id);

    /**
     * select children ids with current id
     *
     * @param id current id
     * @return children ids with current id
     */
    List<Long> selectChildrenIdsWithCurrentIdById(Long id);

    /**
     * select parent ids with current id
     *
     * @param id current id
     * @return parent ids with current id
     */
    List<Long> selectParentIdsWithCurrentIdById(Long id);
}

