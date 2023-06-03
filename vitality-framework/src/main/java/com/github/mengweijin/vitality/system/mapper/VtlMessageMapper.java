package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlMessageDTO;
import com.github.mengweijin.vitality.system.entity.VtlMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface VtlMessageMapper extends BaseMapper<VtlMessage> {

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlMessageDTO
     * @return IPage
     */
    IPage<VtlMessageDTO> page(IPage<VtlMessageDTO> page, @Param("p") VtlMessageDTO dto);

    VtlMessageDTO detail(Long id);
}
