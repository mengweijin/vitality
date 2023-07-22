package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.frontend.layui.LayuiTreeNode;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
import com.github.mengweijin.vitality.system.service.VtlDeptService;
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
import java.util.List;

/**
 * 部门管理表 控制器
 *
 * @author mengweijin
 * @since 2023-06-18
 */
@RestController
@RequestMapping("/vtl-dept")
public class VtlDeptController extends BaseController {

    @Autowired
    private VtlDeptService vtlDeptService;

    @SaCheckPermission("system:dept:add")
    @PostMapping
    public R add(VtlDept vtlDept) {
        boolean bool = vtlDeptService.save(vtlDept);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dept:edit")
    @PutMapping
    public R edit(VtlDept vtlDept) {
        boolean bool = vtlDeptService.updateById(vtlDept);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dept:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = vtlDeptService.setDisabledValue(id, disabled);
        return R.bool(bool);
    }

    @SaCheckPermission("system:dept:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlDeptService.removeById(id);
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlDept getById(@PathVariable("id") Long id) {
        return vtlDeptService.getById(id);
    }

    @SaCheckPermission("system:dept:detail")
    @GetMapping("/detail/{id}")
    public VtlDeptDTO detailById(@PathVariable("id") Long id) {
        return vtlDeptService.detailById(id);
    }

    @SaCheckPermission("system:dept:list")
    @GetMapping("/page")
    public IPage<VtlDeptDTO> page(Page<VtlDeptDTO> page, VtlDeptDTO dto) {
        return vtlDeptService.page(page, dto);
    }

    @SaCheckPermission("system:dept:list")
    @GetMapping("/treeTableDataList")
    public List<VtlDeptDTO> treeTableDataList(VtlDeptDTO dto) {
        return vtlDeptService.treeTableDataList(dto);
    }

    @GetMapping("/layuiTree")
    public List<LayuiTreeNode> layuiTree() {
        return vtlDeptService.layuiTree();
    }

    @SaCheckPermission("system:dept:assignUser")
    @PostMapping("/addUser/{id}")
    public R addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlDeptService.addUsers(id, ListUtil.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        vtlDeptService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }

    @SaCheckPermission("system:dept:authorization")
    @PostMapping("/setMenu/{id}")
    public R setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        vtlDeptService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }
}
