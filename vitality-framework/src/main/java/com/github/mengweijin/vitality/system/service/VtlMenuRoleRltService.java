package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.entity.VtlMenuRoleRlt;
import com.github.mengweijin.vitality.system.mapper.VtlMenuRoleRltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单-角色关联表 服务类
 *
 * @author mengweijin
 * @since 2023-07-02
 */
@Service
public class VtlMenuRoleRltService extends ServiceImpl<VtlMenuRoleRltMapper, VtlMenuRoleRlt> {

    @Autowired
    private VtlMenuRoleRltMapper vtlMenuRoleRltMapper;

}
