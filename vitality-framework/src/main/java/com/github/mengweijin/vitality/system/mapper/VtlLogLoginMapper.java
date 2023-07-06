package com.github.mengweijin.vitality.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.system.dto.VtlLogLoginDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 登录日志记录表 Mapper 接口
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@Mapper
public interface VtlLogLoginMapper extends BaseMapper<VtlLogLogin> {

    /**
     * Get VtlLogLogin detail by id
     * @param id id
     */
    VtlLogLoginDTO detailById(Long id);

    /**
     * 自定义分页
     * @param page page
     * @param dto VtlLogLoginDTO
     * @return IPage
     */
    IPage<VtlLogLoginDTO> page(IPage<VtlLogLoginDTO> page, @Param("p") VtlLogLoginDTO dto);

}
