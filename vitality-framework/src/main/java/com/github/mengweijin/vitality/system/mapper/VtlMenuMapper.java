package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface VtlMenuMapper extends BaseMapper<VtlMenu> {
    /**
     * 自定义分页
     * @param page page
     * @param dto VtlMenuDTO
     * @return IPage
     */
    IPage<VtlMenuDTO> page(IPage<VtlMenuDTO> page, @Param("p") VtlMenuDTO dto);

}
