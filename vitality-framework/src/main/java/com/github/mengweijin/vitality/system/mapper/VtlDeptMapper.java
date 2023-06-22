package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
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
public interface VtlDeptMapper extends BaseMapper<VtlDept> {

    /**
     * Get VtlDept detail by id
     * @param id id
     */
    VtlDeptDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlDeptDTO
     * @return IPage
     */
    IPage<VtlDeptDTO> page(IPage<VtlDeptDTO> page, @Param("p") VtlDeptDTO dto);

    List<VtlDeptDTO> treeTableDataList(@Param("p") VtlDeptDTO dto);

    List<VtlDeptDTO> getByUserId(Long userId);
}
