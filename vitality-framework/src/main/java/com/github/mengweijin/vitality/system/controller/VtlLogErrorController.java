package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.entity.VtlLogError;
import com.github.mengweijin.vitality.system.service.VtlLogErrorService;
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
@RequestMapping("/vtl-log-error")
public class VtlLogErrorController {
    @Autowired
    private VtlLogErrorService errorLogService;

    @PostMapping
    public R add(VtlLogError vtlLogError) {
        boolean bool = errorLogService.save(vtlLogError);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlLogError vtlLogError) {
        boolean bool = errorLogService.updateById(vtlLogError);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = errorLogService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = errorLogService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlLogError getById(@PathVariable("id") Long id) {
        return errorLogService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlLogError> page(Page<VtlLogError> page, VtlLogError vtlLogError) {
        return errorLogService.page(page, new QueryWrapper<>(vtlLogError));
    }
}
