package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.dto.VtlMenuDTO;
import com.github.mengweijin.vitality.system.dto.VtlMenuTreeDataDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.service.VtlMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@RestController
@RequestMapping("/vtl-menu")
public class VtlMenuController {
    @Autowired
    private VtlMenuService menuService;

    @SaCheckPermission("system:menu:add")
    @PostMapping
    public R add(VtlMenu vtlMenu) {
        boolean bool = menuService.save(vtlMenu);
        return R.bool(bool);
    }

    @SaCheckPermission("system:menu:edit")
    @PutMapping
    public R edit(VtlMenu vtlMenu) {
        boolean bool = menuService.updateById(vtlMenu);
        return R.bool(bool);
    }

    @SaCheckPermission("system:menu:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = menuService.removeById(id);
        return R.bool(bool);
    }

    @SaCheckPermission("system:menu:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = menuService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlMenu getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    @SaCheckPermission("system:menu:list")
    @GetMapping("/page")
    public IPage<VtlMenuDTO> page(Page<VtlMenuDTO> page, VtlMenuDTO dto) {
        return menuService.page(page, dto);
    }

    @GetMapping("/treeLeftSideData")
    public List<VtlMenuTreeDataDTO> treeLeftSideData() {
        return menuService.treeLeftSideData();
    }

    @SaCheckPermission("system:menu:list")
    @GetMapping("/treeTableDataList")
    public List<VtlMenuDTO> treeTableDataList(VtlMenuDTO dto) {
        return menuService.treeTableDataList(dto);
    }

    @GetMapping("/titleHierarchy/{id}")
    public String titleHierarchyById(@PathVariable("id") Long id) {
        return menuService.titleHierarchyById(id);
    }

    @SaCheckPermission("system:menu:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = menuService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @GetMapping("/byRole/{roleId}")
    public List<Long> byRole(@PathVariable("roleId") Long roleId) {
        return menuService.byRole(roleId);
    }

    @GetMapping("/byDept/{deptId}")
    public List<Long> byDept(@PathVariable("deptId") Long deptId) {
        return menuService.byDept(deptId);
    }

    @GetMapping("/byPost/{postId}")
    public List<Long> byPost(@PathVariable("postId") Long postId) {
        return menuService.byPost(postId);
    }

    @GetMapping("/byUser/{userId}")
    public List<Long> byUser(@PathVariable("userId") Long userId) {
        return menuService.byUser(userId);
    }

}
