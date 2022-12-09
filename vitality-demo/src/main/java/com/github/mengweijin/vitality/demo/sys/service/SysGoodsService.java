package com.github.mengweijin.vitality.demo.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.demo.sys.dto.SysGoodsDTO;
import com.github.mengweijin.vitality.demo.sys.entity.SysGoods;
import com.github.mengweijin.vitality.demo.sys.mapper.SysGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品表 服务类
 *
 * @author mengweijin
 * @since 2022-12-09
 */
@Service
public class SysGoodsService extends ServiceImpl<SysGoodsMapper, SysGoods> {

    @Autowired
    private SysGoodsMapper sysGoodsMapper;

    public IPage<SysGoodsDTO> page(IPage<SysGoodsDTO> page, SysGoodsDTO dto){
        return sysGoodsMapper.page(page, dto);
    }
}
