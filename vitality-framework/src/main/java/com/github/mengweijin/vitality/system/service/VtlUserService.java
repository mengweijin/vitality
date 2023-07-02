package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.dto.VtlUserChangePasswordDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserEditDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenuUserRlt;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.entity.VtlUserDeptRlt;
import com.github.mengweijin.vitality.system.entity.VtlUserPostRlt;
import com.github.mengweijin.vitality.system.entity.VtlUserRoleRlt;
import com.github.mengweijin.vitality.system.mapper.VtlUserMapper;
import com.github.mengweijin.vitality.system.mapper.VtlUserProfileMapper;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private VtlUserDeptRltService vtlUserDeptRltService;
    @Autowired
    private VtlUserPostRltService vtlUserPostRltService;
    @Autowired
    private VtlUserRoleRltService vtlUserRoleRltService;
    @Autowired
    private VtlUserMapper vtlUserMapper;
    @Autowired
    private VtlUserProfileMapper vtlUserProfileMapper;
    @Autowired
    private VtlMenuUserRltService vtlMenuUserRltService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(VtlUser entity) {
        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(entity.getPassword(), salt);
        entity.setPwdSalt(salt);
        entity.setPassword(hashedPwd);
        super.save(entity);

        if(entity instanceof VtlUserEditDTO dto) {
            this.updateToDept(entity.getId(), dto.getDeptIdList());
            this.updateToPost(entity.getId(), dto.getPostIdList());
            this.updateToRole(entity.getId(), dto.getRoleIdList());
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(VtlUser entity) {
        if(entity instanceof VtlUserEditDTO dto) {
            this.updateToDept(entity.getId(), dto.getDeptIdList());
            this.updateToPost(entity.getId(), dto.getPostIdList());
            this.updateToRole(entity.getId(), dto.getRoleIdList());
        }
        return super.updateById(entity);
    }

    private void updateToDept(Long id, List<Long> deptIdList) {
        if(CollUtil.isEmpty(deptIdList)) {
            return;
        }
        vtlUserDeptRltService.lambdaUpdate().eq(VtlUserDeptRlt::getUserId, id).remove();
        List<VtlUserDeptRlt> list = deptIdList.stream().map(deptId -> new VtlUserDeptRlt().setUserId(id).setDeptId(deptId)).toList();
        vtlUserDeptRltService.saveBatch(list);
    }

    private void updateToPost(Long id, List<Long> postIdList) {
        if(CollUtil.isEmpty(postIdList)) {
            return;
        }
        vtlUserPostRltService.lambdaUpdate().eq(VtlUserPostRlt::getUserId, id).remove();
        List<VtlUserPostRlt> list = postIdList.stream().map(deptId -> new VtlUserPostRlt().setUserId(id).setPostId(deptId)).toList();
        vtlUserPostRltService.saveBatch(list);
    }

    private void updateToRole(Long id, List<Long> roleIdList) {
        if(CollUtil.isEmpty(roleIdList)) {
            return;
        }
        vtlUserRoleRltService.lambdaUpdate().eq(VtlUserRoleRlt::getUserId, id).remove();
        List<VtlUserRoleRlt> list = roleIdList.stream().map(deptId -> new VtlUserRoleRlt().setUserId(id).setRoleId(deptId)).toList();
        vtlUserRoleRltService.saveBatch(list);
    }

    public VtlUserDetailDTO detailById(Long id) {
        VtlUserDetailDTO dto = vtlUserMapper.detailById(id);
        //dto.setIdCardNumber(IdcardUtil.hide(dto.getIdCardNumber(), 6, 14));
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

    public IPage<VtlUserDTO> pageByDept(Page<VtlUserDTO> page, Long deptId, VtlUserDTO dto) {
        dto.setDeleted(0);
        return vtlUserMapper.pageByDept(page, deptId, dto);
    }

    public IPage<VtlUserDTO> pageByPost(Page<VtlUserDTO> page, Long postId, VtlUserDTO dto) {
        dto.setDeleted(0);
        return vtlUserMapper.pageByPost(page, postId, dto);
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

    @Transactional
    public void setMenu(Long id, List<Long> menuIdList) {
        vtlMenuUserRltService.lambdaUpdate().eq(VtlMenuUserRlt::getUserId, id).remove();
        if(CollUtil.isEmpty(menuIdList)) {
            return;
        }
        List<VtlMenuUserRlt> list = menuIdList.stream().map(menuId -> new VtlMenuUserRlt().setUserId(id).setMenuId(menuId)).toList();
        vtlMenuUserRltService.saveBatch(list);
    }
}
