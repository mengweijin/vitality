package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.monitor.domain.entity.LogAlert;
import com.github.mengweijin.vitality.monitor.service.LogAlertService;
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
 *  LogAlert Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/log-alert")
public class LogAlertController {

    private LogAlertService logAlertService;

    /**
     * <p>
     * Get LogAlert page by LogAlert
     * </p>
     * @param page page
     * @param logAlert {@link LogAlert}
     * @return Page<LogError>
     */
    @SaCheckPermission("system:logAlert:query")
    @GetMapping("/page")
    public IPage<LogAlert> page(Page<LogAlert> page, LogAlert logAlert) {
        return logAlertService.page(page, logAlert);
    }

    /**
     * <p>
     * Get LogAlert list by LogAlert
     * </p>
     * @param logAlert {@link LogAlert}
     * @return List<LogError>
     */
    @SaCheckPermission("system:logAlert:query")
    @GetMapping("/list")
    public List<LogAlert> list(LogAlert logAlert) {
        return logAlertService.list(new LambdaQueryWrapper<>(logAlert));
    }

    /**
     * <p>
     * Get LogAlert by id
     * </p>
     * @param id id
     * @return LogError
     */
    @SaCheckPermission("system:logAlert:query")
    @GetMapping("/{id}")
    public LogAlert getById(@PathVariable("id") Long id) {
        return logAlertService.getById(id);
    }

    /**
     * <p>
     * Add LogAlert
     * </p>
     * @param logAlert {@link LogAlert}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:logAlert:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogAlert logAlert) {
        boolean bool = logAlertService.save(logAlert);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update LogAlert
     * </p>
     * @param logAlert {@link LogAlert}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:logAlert:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody LogAlert logAlert) {
        boolean bool = logAlertService.updateById(logAlert);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete LogAlert by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:logAlert:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logAlertService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

