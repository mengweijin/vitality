package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.LogErrorDTO;
import com.github.mengweijin.vitality.system.entity.LogErrorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统错误日志记录表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@Mapper
public interface LogErrorMapper extends BaseMapper<LogErrorDO> {

    /**
     * Get VtlLogError detail by id
     * @param id id
     */
    LogErrorDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlLogErrorDTO
     * @return IPage
     */
    IPage<LogErrorDTO> page(IPage<LogErrorDTO> page, @Param("p") LogErrorDTO dto);

}
