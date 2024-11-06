package com.github.mengweijin.vitality.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vitality.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vitality.monitor.domain.entity.LogError;
import com.github.mengweijin.vitality.monitor.service.LogErrorService;
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
 *  LogError Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/log-error")
public class LogErrorController {

    private LogErrorService logErrorService;

    /**
     * <p>
     * Get LogError page by LogError
     * </p>
     * @param page page
     * @param logError {@link LogError}
     * @return Page<LogError>
     */
    @SaCheckPermission("system:logError:query")
    @GetMapping("/page")
    public IPage<LogError> page(Page<LogError> page, LogError logError) {
        return logErrorService.page(page, logError);
    }

    /**
     * <p>
     * Get LogError list by LogError
     * </p>
     * @param logError {@link LogError}
     * @return List<LogError>
     */
    @SaCheckPermission("system:logError:query")
    @GetMapping("/list")
    public List<LogError> list(LogError logError) {
        return logErrorService.list(new LambdaQueryWrapper<>(logError));
    }

    /**
     * <p>
     * Get LogError by id
     * </p>
     * @param id id
     * @return LogError
     */
    @SaCheckPermission("system:logError:query")
    @GetMapping("/{id}")
    public LogError getById(@PathVariable("id") Long id) {
        return logErrorService.getById(id);
    }

    /**
     * <p>
     * Add LogError
     * </p>
     * @param logError {@link LogError}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:logError:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogError logError) {
        boolean bool = logErrorService.save(logError);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update LogError
     * </p>
     * @param logError {@link LogError}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:logError:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody LogError logError) {
        boolean bool = logErrorService.updateById(logError);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete LogError by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:logError:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logErrorService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

