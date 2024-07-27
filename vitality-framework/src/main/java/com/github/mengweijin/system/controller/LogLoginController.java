package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.mvc.BaseController;
import com.github.mengweijin.system.dto.LogLoginDTO;
import com.github.mengweijin.system.entity.LogLoginDO;
import com.github.mengweijin.system.service.LogLoginService;
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

/**
 * 登录日志记录表 控制器
 *
 * @author mengweijin
 * @since 2023-07-06
 */
@RestController
@RequestMapping("/vtl-log-login")
public class LogLoginController extends BaseController {

    @Autowired
    private LogLoginService logLoginService;

    @PostMapping
    public R add(LogLoginDO logLoginDO) {
        boolean bool = logLoginService.save(logLoginDO);
        return R.ajax(bool);
    }

    @PutMapping
    public R edit(LogLoginDO logLoginDO) {
        boolean bool = logLoginService.updateById(logLoginDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:loginLog:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = logLoginService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:loginLog:delete")
    @DeleteMapping
    public R delete(@RequestParam(value = "ids[]") Long[] ids) {
        boolean bool = logLoginService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public LogLoginDO getById(@PathVariable("id") Long id) {
        return logLoginService.getById(id);
    }

    @SaCheckPermission("system:loginLog:detail")
    @GetMapping("/detail/{id}")
    public LogLoginDTO detailById(@PathVariable("id") Long id) {
        return logLoginService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<LogLoginDTO> page(Page<LogLoginDTO> page, LogLoginDTO dto) {
        return logLoginService.page(page, dto);
    }
}
