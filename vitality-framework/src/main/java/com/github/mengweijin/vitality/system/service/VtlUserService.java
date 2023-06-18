package com.github.mengweijin.vitality.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import com.github.mengweijin.vitality.system.mapper.VtlUserProfileMapper;
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

    @Autowired
    private VtlUserProfileMapper vtlUserProfileMapper;

    public VtlUserDetailDTO detailById(Long id) {
        VtlUserDetailDTO dto = vtlUserMapper.detailById(id);

        //dto.setDeptList();
        //dto.setRoleList();
        //dto.setPostList();

        return dto;
    }

    public IPage<VtlUserDTO> page(IPage<VtlUserDTO> page, VtlUserDTO dto){
        dto.setDeleted(0);
        return vtlUserMapper.page(page, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlUser::getDisabled, disabled).eq(VtlUser::getId, id).update();
    }

}
