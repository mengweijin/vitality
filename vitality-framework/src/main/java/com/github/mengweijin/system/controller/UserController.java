package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.constant.ConfigConst;
import com.github.mengweijin.system.dto.UserChangePasswordDTO;
import com.github.mengweijin.system.dto.UserDTO;
import com.github.mengweijin.system.dto.UserDetailDTO;
import com.github.mengweijin.system.dto.UserEditDTO;
import com.github.mengweijin.system.entity.ConfigDO;
import com.github.mengweijin.system.service.ConfigService;
import com.github.mengweijin.system.service.UserProfileService;
import com.github.mengweijin.system.service.UserService;
import jakarta.validation.Valid;
import org.dromara.hutool.core.collection.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 用户表 控制器
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@RestController
@RequestMapping("/vtl-user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private ConfigService configService;

    @SaCheckPermission("system:user:add")
    @PostMapping
    public R add(UserEditDTO vtlUser) {
        boolean bool = userService.save(vtlUser);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:user:edit")
    @PutMapping
    public R edit(UserEditDTO vtlUser) {
        boolean bool = userService.updateById(vtlUser);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:user:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = userService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:user:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = userService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {
        return userService.detailById(id);
    }

    @SaCheckPermission("system:user:detail")
    @GetMapping("/detail/{id}")
    public UserDetailDTO detailById(@PathVariable("id") Long id) {
        return userService.detailById(id);
    }

    @SaCheckPermission("system:user:list")
    @GetMapping("/page")
    public IPage<UserDTO> page(Page<UserDTO> page, UserDTO dto, Long deptId) {
        return userService.page(page, dto, deptId);
    }

    @GetMapping("/page/byRole/{roleId}")
    public IPage<UserDTO> pageByRole(@PathVariable("roleId") Long roleId, Page<UserDTO> page, UserDTO dto) {
        return userService.pageByRole(page, roleId, dto);
    }

    @GetMapping("/page/byDept/{deptId}")
    public IPage<UserDTO> pageByDept(@PathVariable("deptId") Long deptId, Page<UserDTO> page, UserDTO dto) {
        return userService.pageByDept(page, deptId, dto);
    }

    @GetMapping("/page/byPost/{postId}")
    public IPage<UserDTO> pageByPost(@PathVariable("postId") Long postId, Page<UserDTO> page, UserDTO dto) {
        return userService.pageByPost(page, postId, dto);
    }

    @SaCheckPermission("system:user:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = userService.setDisabledValue(id, disabled);
        return R.ajax(bool);
    }

    @PostMapping("/updateProfile/{id}")
    public R updateProfile(@PathVariable("id") Long id, String profilePicture) {
        boolean bool = userProfileService.updateProfileById(id, profilePicture);
        return R.ajax(bool);
    }

    @PostMapping("/changePassword")
    public R changePassword(@Valid UserChangePasswordDTO dto) {
        return R.ajax(userService.changePassword(dto));
    }

    @SaCheckPermission("system:user:resetPassword")
    @PostMapping("/resetPassword/{id}")
    public R resetPassword(@PathVariable("id") Long id) {
        ConfigDO config = configService.getByCode(ConfigConst.CODE_USER_INIT_PASSWORD);
        boolean bool = userService.updatePassword(id, config.getVal());
        return R.ajax(bool);
    }

    @SaCheckPermission("system:user:authorization")
    @PostMapping("/setMenu/{id}")
    public R setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        userService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }

}
