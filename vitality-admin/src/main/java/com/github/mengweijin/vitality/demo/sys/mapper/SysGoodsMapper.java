package com.github.mengweijin.vitality.demo.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vitality.demo.sys.dto.SysGoodsDTO;
import com.github.mengweijin.vitality.demo.sys.entity.SysGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品表 Mapper 接口
 *
 * @author mengweijin
 * @since 2022-12-09
 */
@Mapper
public interface SysGoodsMapper extends BaseMapper<SysGoods> {

    /**
     * 自定义分页
     * @param page page
     * @param dto SysGoodsDTO
     * @return IPage
     */
    IPage<SysGoodsDTO> page(IPage<SysGoodsDTO> page, @Param("p") SysGoodsDTO dto);

}
