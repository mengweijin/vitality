package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.entity.VtlErrorLog;
import com.github.mengweijin.vitality.system.service.VtlErrorLogService;
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
@RequestMapping("/vtl-error-log")
public class SysErrorLogController {
    @Autowired
    private VtlErrorLogService vtlErrorLogService;

    @PostMapping
    public R add(VtlErrorLog vtlErrorLog) {
        boolean bool = vtlErrorLogService.save(vtlErrorLog);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(VtlErrorLog vtlErrorLog) {
        boolean bool = vtlErrorLogService.updateById(vtlErrorLog);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = vtlErrorLogService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = vtlErrorLogService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public VtlErrorLog getById(@PathVariable("id") Long id) {
        return vtlErrorLogService.getById(id);
    }

    @GetMapping("/page")
    public IPage<VtlErrorLog> page(Page<VtlErrorLog> page, VtlErrorLog vtlErrorLog) {
        return vtlErrorLogService.page(page, new QueryWrapper<>(vtlErrorLog));
    }
}
