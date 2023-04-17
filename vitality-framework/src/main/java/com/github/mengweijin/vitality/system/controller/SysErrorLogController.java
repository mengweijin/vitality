package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.system.entity.SysErrorLog;
import com.github.mengweijin.vitality.system.service.SysErrorLogService;
import com.github.mengweijin.vitality.framework.domain.R;
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
@RequestMapping("/sys-error-log")
public class SysErrorLogController {
    @Autowired
    private SysErrorLogService sysErrorLogService;

    @PostMapping
    public R add(SysErrorLog sysErrorLog) {
        boolean bool = sysErrorLogService.save(sysErrorLog);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(SysErrorLog sysErrorLog) {
        boolean bool = sysErrorLogService.updateById(sysErrorLog);
        return R.bool(bool);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = sysErrorLogService.removeById(id);
        return R.bool(bool);
    }

    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = sysErrorLogService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public SysErrorLog getById(@PathVariable("id") Long id) {
        return sysErrorLogService.getById(id);
    }

    @GetMapping("/page")
    public IPage<SysErrorLog> page(Page<SysErrorLog> page, SysErrorLog sysErrorLog) {
        return sysErrorLogService.page(page, new QueryWrapper<>(sysErrorLog));
    }
}
