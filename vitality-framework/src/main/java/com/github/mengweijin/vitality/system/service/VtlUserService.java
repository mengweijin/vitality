package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.dto.VtlUserChangePasswordDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import com.github.mengweijin.vitality.system.mapper.VtlUserProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        dto.setDeptList(deptService.getByUserId(id));
        dto.setRoleList(roleService.getByUserId(id));
        dto.setPostList(postService.getByUserId(id));
        return dto;
    }

    public IPage<VtlUserDTO> page(IPage<VtlUserDTO> page, VtlUserDTO dto, Long deptId) {
        List<Long> deptIdList = new ArrayList<>();
        if(deptId != null) {
            deptIdList.add(deptId);
            deptIdList.addAll(deptService.getAllChildrenById(deptId));
        }
        dto.setDeleted(0);
        return vtlUserMapper.page(page, dto, deptIdList);
    }

    public IPage<VtlUserDTO> pageByRole(IPage<VtlUserDTO> page, Long roleId, VtlUserDTO dto){
        dto.setDeleted(0);
        return vtlUserMapper.pageByRole(page, roleId, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(VtlUser::getDisabled, disabled).eq(VtlUser::getId, id).update();
    }

    public VtlUser getByUsername(String username) {
        return this.lambdaQuery().eq(VtlUser::getUsername, username).one();
    }

    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    public boolean changePassword(VtlUserChangePasswordDTO dto) {
        VtlUser vtlUser = this.getById(dto.getId());
        boolean checked = this.checkPassword(dto.getPassword(), vtlUser.getPassword());
        if(!checked) {
            throw new ClientException("Old password check failed!");
        }
        return this.updatePassword(dto.getId(), dto.getNewPassword());
    }

    public boolean updatePassword(Long id, String password) {
        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(password, salt);
        return this.lambdaUpdate().set(VtlUser::getPassword, hashedPwd).set(VtlUser::getPwdSalt, salt).eq(VtlUser::getId, id).update();
    }
}
