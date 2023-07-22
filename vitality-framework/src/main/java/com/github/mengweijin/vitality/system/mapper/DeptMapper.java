package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.DeptDTO;
import com.github.mengweijin.vitality.system.entity.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptDO> {

    /**
     * Get VtlDept detail by id
     * @param id id
     */
    DeptDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlDeptDTO
     * @return IPage
     */
    IPage<DeptDTO> page(IPage<DeptDTO> page, @Param("p") DeptDTO dto);

    List<DeptDTO> treeTableDataList(@Param("p") DeptDTO dto);

    List<DeptDTO> getByUserId(Long userId);
}
