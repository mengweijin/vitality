package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
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
 * @author mengweijin
 * @date 2023/4/1
 */
@RestController
@RequestMapping("/vtl-log-operation")
public class VtlLogOperationController {
    @Autowired
    private VtlLogOperationService operationLogService;

    @PostMapping
    public R add(VtlLogOperation vtlLogOperation) {
        boolean bool = operationLogService.save(vtlLogOperation);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlLogOperation vtlLogOperation) {
        boolean bool = operationLogService.updateById(vtlLogOperation);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = operationLogService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = operationLogService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlLogOperation getById(@PathVariable("id") Long id) {
        return operationLogService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlLogOperation> page(Page<VtlLogOperation> page, VtlLogOperation vtlLogOperation) {
        return operationLogService.page(page, new QueryWrapper<>(vtlLogOperation));
    }
}
