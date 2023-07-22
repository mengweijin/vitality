package com.github.mengweijin.vitality.system.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.dto.UserChangePasswordDTO;
import com.github.mengweijin.vitality.system.dto.UserDTO;
import com.github.mengweijin.vitality.system.dto.UserDetailDTO;
import com.github.mengweijin.vitality.system.dto.UserEditDTO;
import com.github.mengweijin.vitality.system.entity.MenuUserRltDO;
import com.github.mengweijin.vitality.system.entity.UserDO;
import com.github.mengweijin.vitality.system.entity.UserDeptRltDO;
import com.github.mengweijin.vitality.system.entity.UserPostRltDO;
import com.github.mengweijin.vitality.system.entity.UserRoleRltDO;
import com.github.mengweijin.vitality.system.mapper.UserMapper;
import com.github.mengweijin.vitality.system.mapper.UserProfileMapper;
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
public class UserService extends ServiceImpl<UserMapper, UserDO> {
    @Autowired
    private ConfigService configService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserDeptRltService userDeptRltService;
    @Autowired
    private UserPostRltService userPostRltService;
    @Autowired
    private UserRoleRltService userRoleRltService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;
    @Autowired
    private MenuUserRltService menuUserRltService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(UserDO entity) {
        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(entity.getPassword(), salt);
        entity.setPwdSalt(salt);
        entity.setPassword(hashedPwd);
        super.save(entity);

        if(entity instanceof UserEditDTO dto) {
            this.updateToDept(entity.getId(), dto.getDeptIdList());
            this.updateToPost(entity.getId(), dto.getPostIdList());
            this.updateToRole(entity.getId(), dto.getRoleIdList());
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(UserDO entity) {
        if(entity instanceof UserEditDTO dto) {
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
        userDeptRltService.lambdaUpdate().eq(UserDeptRltDO::getUserId, id).remove();
        List<UserDeptRltDO> list = deptIdList.stream().map(deptId -> new UserDeptRltDO().setUserId(id).setDeptId(deptId)).toList();
        userDeptRltService.saveBatch(list);
    }

    private void updateToPost(Long id, List<Long> postIdList) {
        if(CollUtil.isEmpty(postIdList)) {
            return;
        }
        userPostRltService.lambdaUpdate().eq(UserPostRltDO::getUserId, id).remove();
        List<UserPostRltDO> list = postIdList.stream().map(deptId -> new UserPostRltDO().setUserId(id).setPostId(deptId)).toList();
        userPostRltService.saveBatch(list);
    }

    private void updateToRole(Long id, List<Long> roleIdList) {
        if(CollUtil.isEmpty(roleIdList)) {
            return;
        }
        userRoleRltService.lambdaUpdate().eq(UserRoleRltDO::getUserId, id).remove();
        List<UserRoleRltDO> list = roleIdList.stream().map(deptId -> new UserRoleRltDO().setUserId(id).setRoleId(deptId)).toList();
        userRoleRltService.saveBatch(list);
    }

    public UserDetailDTO detailById(Long id) {
        UserDetailDTO dto = userMapper.detailById(id);
        //dto.setIdCardNumber(IdcardUtil.hide(dto.getIdCardNumber(), 6, 14));
        dto.setDeptList(deptService.getByUserId(id));
        dto.setRoleList(roleService.getByUserId(id));
        dto.setPostList(postService.getByUserId(id));
        return dto;
    }

    public IPage<UserDTO> page(IPage<UserDTO> page, UserDTO dto, Long deptId) {
        List<Long> deptIdList = new ArrayList<>();
        if(deptId != null) {
            deptIdList.add(deptId);
            deptIdList.addAll(deptService.getAllChildrenById(deptId));
        }
        dto.setDeleted(0);
        return userMapper.page(page, dto, deptIdList);
    }

    public IPage<UserDTO> pageByRole(IPage<UserDTO> page, Long roleId, UserDTO dto){
        dto.setDeleted(0);
        return userMapper.pageByRole(page, roleId, dto);
    }

    public IPage<UserDTO> pageByDept(Page<UserDTO> page, Long deptId, UserDTO dto) {
        dto.setDeleted(0);
        return userMapper.pageByDept(page, deptId, dto);
    }

    public IPage<UserDTO> pageByPost(Page<UserDTO> page, Long postId, UserDTO dto) {
        dto.setDeleted(0);
        return userMapper.pageByPost(page, postId, dto);
    }

    public boolean setDisabledValue(Long id, boolean disabled) {
        return this.lambdaUpdate().set(UserDO::getDisabled, disabled).eq(UserDO::getId, id).update();
    }

    public UserDO getByUsername(String username) {
        return this.lambdaQuery().eq(UserDO::getUsername, username).one();
    }

    public boolean checkPassword(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    public boolean changePassword(UserChangePasswordDTO dto) {
        UserDO userDO = this.getById(dto.getId());
        boolean checked = this.checkPassword(dto.getPassword(), userDO.getPassword());
        if(!checked) {
            throw new ClientException("Old password check failed!");
        }
        return this.updatePassword(dto.getId(), dto.getNewPassword());
    }

    public boolean updatePassword(Long id, String password) {
        String salt = BCrypt.gensalt();
        String hashedPwd = BCrypt.hashpw(password, salt);
        return this.lambdaUpdate().set(UserDO::getPassword, hashedPwd).set(UserDO::getPwdSalt, salt).eq(UserDO::getId, id).update();
    }

    @Transactional
    public void setMenu(Long id, List<Long> menuIdList) {
        menuUserRltService.lambdaUpdate().eq(MenuUserRltDO::getUserId, id).remove();
        if(CollUtil.isEmpty(menuIdList)) {
            return;
        }
        List<MenuUserRltDO> list = menuIdList.stream().map(menuId -> new MenuUserRltDO().setUserId(id).setMenuId(menuId)).toList();
        menuUserRltService.saveBatch(list);
    }

    public void setSessionUser(UserDO user) {
        StpUtil.getSession().set(UserConst.SA_TOKEN_SESSION_USER_KEY, user);
    }

    public UserDO getSessionUser() {
        return (UserDO) StpUtil.getSession().get(UserConst.SA_TOKEN_SESSION_USER_KEY);
    }
}
