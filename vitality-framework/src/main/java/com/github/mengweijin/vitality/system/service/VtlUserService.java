package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
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
    private VtlConfigService configService;
    @Autowired
    private VtlDeptService deptService;
    @Autowired
    private VtlRoleService roleService;
    @Autowired
    private VtlPostService postService;
    @Autowired
    private VtlUserMapper vtlUserMapper;
    @Autowired
    private VtlUserProfileMapper vtlUserProfileMapper;

    @Override
    public boolean save(VtlUser entity) {
        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(entity.getPassword(), salt);
        entity.setPwdSalt(salt);
        entity.setPassword(hashedPwd);
        return super.save(entity);
    }

    public VtlUserDetailDTO detailById(Long id) {
        VtlUserDetailDTO dto = vtlUserMapper.detailById(id);
        // TODO DesensitizedUtil.password(dto.getPassword());
        // TODO DesensitizedUtil.idCardNum(dto.getIdentityCardNumber(), 1, 2);
        // TODO DesensitizedUtil.mobilePhone(dto.getMobilePhone());

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

    public VtlUser getByUsername(String username) {
        return this.lambdaQuery().eq(VtlUser::getUsername, username).one();
    }
}
