package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.constant.ConfigConst;
import com.github.mengweijin.vitality.system.dto.VtlUserChangePasswordDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDTO;
import com.github.mengweijin.vitality.system.dto.VtlUserDetailDTO;
import com.github.mengweijin.vitality.system.entity.VtlConfig;
import com.github.mengweijin.vitality.system.entity.VtlUser;
import com.github.mengweijin.vitality.system.service.VtlConfigService;
import com.github.mengweijin.vitality.system.service.VtlUserProfileService;
import com.github.mengweijin.vitality.system.service.VtlUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class VtlUserController extends BaseController {

    @Autowired
    private VtlUserService vtlUserService;
    @Autowired
    private VtlUserProfileService vtlUserProfileService;
    @Autowired
    private VtlConfigService configService;

    @PostMapping
    public R add(VtlUser vtlUser) {
        boolean bool = vtlUserService.save(vtlUser);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlUser vtlUser) {
        boolean bool = vtlUserService.updateById(vtlUser);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlUserService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlUserService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlUserDTO getById(@PathVariable("id") Long id) {
        return vtlUserService.detailById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlUserDetailDTO detailById(@PathVariable("id") Long id) {
        return vtlUserService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlUserDTO> page(Page<VtlUserDTO> page, VtlUserDTO dto, Long deptId) {
        return vtlUserService.page(page, dto, deptId);
    }

    @GetMapping("/page/byRole/{roleId}")
    public IPage<VtlUserDTO> pageByRole(@PathVariable("roleId") Long roleId, Page<VtlUserDTO> page, VtlUserDTO dto) {
        return vtlUserService.pageByRole(page, roleId, dto);
    }

    @GetMapping("/page/byPost/{postId}")
    public IPage<VtlUserDTO> pageByPost(@PathVariable("postId") Long postId, Page<VtlUserDTO> page, VtlUserDTO dto) {
        return vtlUserService.pageByPost(page, postId, dto);
    }

    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlUserService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @PostMapping("/updateProfile/{id}")
    public R updateProfile(@PathVariable("id") Long id, String profilePicture) {
        boolean bool = vtlUserProfileService.updateProfileById(id, profilePicture);
        return R.bool(bool);
    }

    @PostMapping("/changePassword")
    public R changePassword(@Valid VtlUserChangePasswordDTO dto) {
        return R.bool(vtlUserService.changePassword(dto));
    }

    @PostMapping("/resetPassword/{id}")
    public R resetPassword(@PathVariable("id") Long id) {
        VtlConfig config = configService.getByCode(ConfigConst.CODE_USER_INIT_PASSWORD);
        boolean bool = vtlUserService.updatePassword(id, config.getVal());
        return R.bool(bool);
    }
}
