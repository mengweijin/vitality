package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务类
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@Service
public class VtlUserService extends ServiceImpl<VtlUserMapper, VtlUser> {

    @Autowired
    private VtlUserMapper vtlUserMapper;

    public IPage<VtlUserDTO> page(IPage<VtlUserDTO> page, VtlUserDTO dto){
        dto.setDeleted(false);
        return vtlUserMapper.page(page, dto);
    }

    public boolean disabledChange(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlUser::getDisabled, disabled).eq(VtlUser::getId, id).update();
    }
}
