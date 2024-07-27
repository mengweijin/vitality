package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.LogErrorDTO;
import com.github.mengweijin.system.entity.LogErrorDO;
import com.github.mengweijin.system.service.LogErrorService;
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
 * 系统错误日志记录表 控制器
 *
 * @author mengweijin
 * @since 2023-06-06
 */
@RestController
@RequestMapping("/vtl-log-error")
public class LogErrorController extends BaseController {

    @Autowired
    private LogErrorService logErrorService;

    @PostMapping
    public R add(LogErrorDO logErrorDO) {
        boolean bool = logErrorService.save(logErrorDO);
        return R.ajax(bool);
    }

    @PutMapping
    public R edit(LogErrorDO logErrorDO) {
        boolean bool = logErrorService.updateById(logErrorDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:errorLog:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = logErrorService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:errorLog:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = logErrorService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public LogErrorDO getById(@PathVariable("id") Long id) {
        return logErrorService.getById(id);
    }

    @SaCheckPermission("system:errorLog:detail")
    @GetMapping("/detail/{id}")
    public LogErrorDTO detailById(@PathVariable("id") Long id) {
        return logErrorService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<LogErrorDTO> page(Page<LogErrorDTO> page, LogErrorDTO dto) {
        return logErrorService.page(page, dto);
    }
}
