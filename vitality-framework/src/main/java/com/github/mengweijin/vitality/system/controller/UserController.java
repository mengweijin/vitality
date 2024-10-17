package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.exception.ClientException;
import com.github.mengweijin.vitality.framework.satoken.LoginHelper;
import com.github.mengweijin.vitality.framework.util.BeanUtils;
import com.github.mengweijin.vitality.framework.validator.group.Group;
import com.github.mengweijin.vitality.system.constant.UserConst;
import com.github.mengweijin.vitality.system.domain.bo.ChangePasswordBO;
import com.github.mengweijin.vitality.system.domain.bo.UserBO;
import com.github.mengweijin.vitality.system.domain.bo.UserRolesBO;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.domain.entity.UserAvatar;
import com.github.mengweijin.vitality.system.domain.vo.UserSensitiveVO;
import com.github.mengweijin.vitality.system.domain.vo.UserVO;
import com.github.mengweijin.vitality.system.service.UserAvatarService;
import com.github.mengweijin.vitality.system.service.UserRoleService;
import com.github.mengweijin.vitality.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/user")
public class UserController {

    private UserService userService;

    private UserAvatarService userAvatarService;

    private UserRoleService userRoleService;


    /**
     * <p>
     * Get User page by User
     * </p>
     *
     * @param page page
     * @param user {@link User}
     * @return Page<User>
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/page")
    public IPage<UserVO> page(Page<User> page, User user) {
        IPage<User> userPage = userService.page(page, user);
        return BeanUtils.copyPage(userPage, UserVO.class);
    }

    /**
     * <p>
     * Get User list by User
     * </p>
     *
     * @param user {@link User}
     * @return List<User>
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/list")
    public List<UserVO> list(User user) {
        List<User> userList = userService.list(new LambdaQueryWrapper<>(user));
        return BeanUtils.copyList(userList, UserVO.class);
    }

    /**
     * <p>
     * Get User by id
     * </p>
     *
     * @param id id
     * @return User
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/{id}")
    public UserVO getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtils.copyBean(user, UserVO.class);
    }

    /**
     * <p>
     * Get Sensitive User by id
     * </p>
     *
     * @param id id
     * @return User
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/sensitive/{id}")
    public User getSensitiveById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    /**
     * <p>
     * Get mine User by id
     * </p>
     *
     * @return User
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/mine")
    public UserSensitiveVO getMine() {
        User user = userService.getById(LoginHelper.getLoginUser().getUserId());
        return BeanUtils.copyBean(user, new UserSensitiveVO());
    }

    /**
     * <p>
     * Add User
     * </p>
     *
     * @param user {@link User}
     */
    @SaCheckPermission("system:user:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody UserBO user) {
        boolean bool = userService.save(BeanUtils.copyBean(user, new User()));
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update User
     * </p>
     *
     * @param user {@link User}
     */
    @SaCheckPermission("system:user:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody UserBO user) {
        boolean bool = userService.updateById(BeanUtils.copyBean(user, new User()));
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete User by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @SaCheckPermission("system:user:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        boolean isAdmin = list.stream().anyMatch(id -> UserConst.ADMIN_USER_ID == NumberUtil.parseLong(StrUtil.toString(id)));
        if (isAdmin) {
            throw new ClientException("Can't delete admin account!");
        }
        return R.ajax(userService.removeBatchByIds(list));
    }

    /**
     * <p>
     * change user password
     * </p>
     *
     * @param bo {@link ChangePasswordBO}
     */
    @SaCheckPermission("system:user:changePassword")
    @PostMapping("/change-password")
    public R<Void> changePassword(@Validated @RequestBody ChangePasswordBO bo) {
        boolean bool = userService.changePassword(bo);
        return R.ajax(bool);
    }

    /**
     * <p>
     * change user password
     * </p>
     *
     * @param bo {@link ChangePasswordBO}
     */
    @SaCheckPermission("system:user:resetPassword")
    @PostMapping("/reset-password")
    public R<Void> resetPassword(@Validated @RequestBody ChangePasswordBO bo) {
        boolean bool = userService.updatePassword(bo.getUsername(), bo.getNewPassword());
        return R.ajax(bool);
    }

    /**
     * <p>
     * set user avatar
     * </p>
     *
     * @param userAvatar {@link UserAvatar}
     */
    @SaCheckPermission("system:user:setAvatar")
    @PostMapping("/set-avatar")
    public R<Void> setAvatar(@Validated @RequestBody UserAvatar userAvatar) {
        boolean bool = userAvatarService.setAvatar(userAvatar);
        return R.ajax(bool);
    }

    /**
     * <p>
     * set user Roles
     * </p>
     *
     * @param bo {@link UserRolesBO}
     */
    @SaCheckPermission("system:user:setRoles")
    @PostMapping("/set-roles")
    public R<Void> setRoles(@Validated @RequestBody UserRolesBO bo) {
        boolean bool = userRoleService.setUserRoles(bo);
        return R.ajax(bool);
    }
}

