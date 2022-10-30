package com.github.mengweijin.woodenman.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.woodenman.generator.dto.DatasourceInfoDTO;
import com.github.mengweijin.woodenman.generator.entity.DatasourceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mengweijin
 */
@Mapper
public interface DatasourceMapper extends BaseMapper<DatasourceInfo> {

    /**
     * 自定义分页
     * @param page page
     * @param dto DatasourceInfoDTO
     * @return IPage
     */
    IPage<DatasourceInfoDTO> selectPageVO(IPage<DatasourceInfoDTO> page, @Param("param") DatasourceInfoDTO dto);

}
