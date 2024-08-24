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

    List<Dept> selectWithParentByChildrenId(Long childrenId);

    List<Dept> selectChildrenByParentId(Long parentId);

    Dept selectByUserId(Long userId);
}

