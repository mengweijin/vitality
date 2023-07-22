package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.mvc.BaseController;
import com.github.mengweijin.vitality.system.dto.LogOperationDTO;
import com.github.mengweijin.vitality.system.entity.LogOperationDO;
import com.github.mengweijin.vitality.system.service.LogOperationService;
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
public class LogOperationController extends BaseController {

    @Autowired
    private LogOperationService logOperationService;

    @PostMapping
    public R add(LogOperationDO logOperationDO) {
        boolean bool = logOperationService.save(logOperationDO);
        return R.bool(bool);
    }

    @PutMapping
    public R edit(LogOperationDO logOperationDO) {
        boolean bool = logOperationService.updateById(logOperationDO);
        return R.bool(bool);
    }

    @SaCheckPermission("system:operationLog:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = logOperationService.removeById(id);
        return R.bool(bool);
    }

    @SaCheckPermission("system:operationLog:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = logOperationService.removeBatchByIds(Arrays.asList(ids));
        return R.bool(bool);
    }

    @GetMapping("/{id}")
    public LogOperationDO getById(@PathVariable("id") Long id) {
        return logOperationService.getById(id);
    }

    @SaCheckPermission("system:operationLog:detail")
    @GetMapping("/detail/{id}")
    public LogOperationDTO detailById(@PathVariable("id") Long id) {
        return logOperationService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<LogOperationDTO> page(Page<LogOperationDTO> page, LogOperationDTO dto) {
        return logOperationService.page(page, dto);
    }
}
