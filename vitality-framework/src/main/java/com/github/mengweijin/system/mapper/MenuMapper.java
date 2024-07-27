package com.github.mengweijin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.system.dto.MenuDTO;
import com.github.mengweijin.system.entity.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
    /**
     * 自定义分页
     * @param page page
     * @param dto VtlMenuDTO
     * @return IPage
     */
    IPage<MenuDTO> page(IPage<MenuDTO> page, @Param("p") MenuDTO dto);

    List<MenuDTO> treeTableDataList(@Param("p") MenuDTO dto);

}
