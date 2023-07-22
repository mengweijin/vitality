package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.LogOperationDTO;
import com.github.mengweijin.vitality.system.entity.LogOperationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统操作日志表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Mapper
public interface LogOperationMapper extends BaseMapper<LogOperationDO> {

    /**
     * Get VtlLogOperation detail by id
     * @param id id
     */
    LogOperationDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlLogOperationDTO
     * @return IPage
     */
    IPage<LogOperationDTO> page(IPage<LogOperationDTO> page, @Param("p") LogOperationDTO dto);

}
