package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlDeptDTO;
import com.github.mengweijin.vitality.system.entity.VtlDept;
import com.github.mengweijin.vitality.system.service.VtlDeptService;
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

    @PostMapping
    public R add(VtlDept vtlDept) {
        boolean bool = vtlDeptService.save(vtlDept);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlDept vtlDept) {
        boolean bool = vtlDeptService.updateById(vtlDept);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlDeptService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlDeptService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlDept getById(@PathVariable("id") Long id) {
        return vtlDeptService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlDeptDTO detailById(@PathVariable("id") Long id) {
        return vtlDeptService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlDeptDTO> page(Page<VtlDeptDTO> page, VtlDeptDTO dto) {
        return vtlDeptService.page(page, dto);
    }
}
