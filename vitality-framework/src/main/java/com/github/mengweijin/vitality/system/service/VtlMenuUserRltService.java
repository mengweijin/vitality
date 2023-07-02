package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlMenuUserRlt;
import com.github.mengweijin.vitality.system.mapper.VtlMenuUserRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-用户关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class VtlMenuUserRltService extends ServiceImpl<VtlMenuUserRltMapper, VtlMenuUserRlt> {

    @Autowired
    private VtlMenuUserRltMapper vtlMenuUserRltMapper;

}
