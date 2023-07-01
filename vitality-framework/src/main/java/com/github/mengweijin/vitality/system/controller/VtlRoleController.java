package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.entity.VtlRole;
import com.github.mengweijin.vitality.system.service.VtlRoleService;
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
import java.util.List;

/**
 * 角色管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@RestController
@RequestMapping("/vtl-role")
public class VtlRoleController extends BaseController {

    @Autowired
    private VtlRoleService vtlRoleService;

    @PostMapping
    public R add(VtlRole vtlRole) {
        boolean bool = vtlRoleService.save(vtlRole);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlRole vtlRole) {
        boolean bool = vtlRoleService.updateById(vtlRole);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlRoleService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlRoleService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlRole getById(@PathVariable("id") Long id) {
        return vtlRoleService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlRoleDTO detailById(@PathVariable("id") Long id) {
        return vtlRoleService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlRoleDTO> page(Page<VtlRoleDTO> page, VtlRoleDTO dto) {
        return vtlRoleService.page(page, dto);
    }

    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlRoleService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @PostMapping("/addUser/{id}")
    public R addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlRoleService.addUsers(id, List.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlRoleService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }
}
