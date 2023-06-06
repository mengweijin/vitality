package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.VtlLogOperationDTO;
import com.github.mengweijin.vitality.system.entity.VtlLogOperation;
import com.github.mengweijin.vitality.system.service.VtlLogOperationService;
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
 * 系统操作日志表 控制器
 *
 * @author mengweijin
 * @since 2023-05-28
 */
@RestController
@RequestMapping("/vtl-log-operation")
public class VtlLogOperationController extends BaseController {

    @Autowired
    private VtlLogOperationService vtlLogOperationService;

    @PostMapping
    public R add(VtlLogOperation vtlLogOperation) {
        boolean bool = vtlLogOperationService.save(vtlLogOperation);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlLogOperation vtlLogOperation) {
        boolean bool = vtlLogOperationService.updateById(vtlLogOperation);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlLogOperationService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlLogOperationService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlLogOperation getById(@PathVariable("id") Long id) {
        return vtlLogOperationService.getById(id);
    }

    @GetMapping("/detail/{id}")
    public VtlLogOperationDTO detailById(@PathVariable("id") Long id) {
        return vtlLogOperationService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlLogOperationDTO> page(Page<VtlLogOperationDTO> page, VtlLogOperationDTO dto) {
        return vtlLogOperationService.page(page, dto);
    }
}
