package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.monitor.domain.entity.LogLogin;
import com.github.mengweijin.vitality.monitor.service.LogLoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  LogLogin Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/log-login")
public class LogLoginController {

    private LogLoginService logLoginService;

    /**
     * <p>
     * Get LogLogin page by LogLogin
     * </p>
     * @param page page
     * @param logLogin {@link LogLogin}
     * @return Page<LogLogin>
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/page")
    public IPage<LogLogin> page(Page<LogLogin> page, LogLogin logLogin) {
        return logLoginService.page(page, logLogin);
    }

    /**
     * <p>
     * Get LogLogin list by LogLogin
     * </p>
     * @param logLogin {@link LogLogin}
     * @return List<LogLogin>
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/list")
    public List<LogLogin> list(LogLogin logLogin) {
        return logLoginService.list(new LambdaQueryWrapper<>(logLogin));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return LogLogin
     */
    @SaCheckPermission("system:logLogin:query")
    @GetMapping("/{id}")
    public LogLogin getById(@PathVariable("id") Long id) {
        return logLoginService.getById(id);
    }

    /**
     * <p>
     * Add LogLogin
     * </p>
     * @param logLogin {@link LogLogin}
     */
    @SaCheckPermission("system:logLogin:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogLogin logLogin) {
        boolean bool = logLoginService.save(logLogin);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update LogLogin
     * </p>
     * @param logLogin {@link LogLogin}
     */
    @SaCheckPermission("system:logLogin:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody LogLogin logLogin) {
        boolean bool = logLoginService.updateById(logLogin);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete LogLogin by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:logLogin:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logLoginService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

