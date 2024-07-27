package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.DeptDTO;
import com.github.mengweijin.system.entity.DeptDO;
import com.github.mengweijin.system.service.DeptService;
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
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @SaCheckPermission("system:dept:add")
    @PostMapping
    public R<Void> add(DeptDO deptDO) {
        boolean bool = deptService.save(deptDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dept:edit")
    @PutMapping
    public R<Void> edit(DeptDO deptDO) {
        boolean bool = deptService.updateById(deptDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dept:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R<Void> setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = deptService.setDisabledValue(id, disabled);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:dept:delete")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable("id") Long id) {
        boolean bool = deptService.removeById(id);
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public DeptDO getById(@PathVariable("id") Long id) {
        return deptService.getById(id);
    }

    @SaCheckPermission("system:dept:detail")
    @GetMapping("/detail/{id}")
    public DeptDTO detailById(@PathVariable("id") Long id) {
        return deptService.detailById(id);
    }

    @SaCheckPermission("system:dept:list")
    @GetMapping("/page")
    public IPage<DeptDTO> page(Page<DeptDTO> page, DeptDTO dto) {
        return deptService.page(page, dto);
    }

    @SaCheckPermission("system:dept:list")
    @GetMapping("/treeTableDataList")
    public List<DeptDTO> treeTableDataList(DeptDTO dto) {
        return deptService.treeTableDataList(dto);
    }

    @SaCheckPermission("system:dept:assignUser")
    @PostMapping("/addUser/{id}")
    public R<Void> addUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        deptService.addUsers(id, ListUtil.of(userIdList));
        return R.success();
    }

    @DeleteMapping("/removeUser/{id}")
    public R<Void> removeUsers(@PathVariable("id") Long id, @RequestParam(value = "userIdList[]") Long[] userIdList) {
        deptService.removeUsers(id, Arrays.asList(userIdList));
        return R.success();
    }

    @SaCheckPermission("system:dept:authorization")
    @PostMapping("/setMenu/{id}")
    public R<Void> setMenu(@PathVariable("id") Long id, @RequestParam(value = "menuIdList[]", required = false) Long[] menuIdList) {
        deptService.setMenu(id, ListUtil.of(menuIdList));
        return R.success();
    }
}
