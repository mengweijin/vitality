package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.system.domain.entity.LogOperation;
import com.github.mengweijin.vita.system.service.LogOperationService;
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
 *  LogOperation Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/log-operation")
public class LogOperationController {

    private LogOperationService logOperationService;

    /**
     * <p>
     * Get LogOperation page by LogOperation
     * </p>
     * @param page page
     * @param logOperation {@link LogOperation}
     * @return Page<LogOperation>
     */
    @SaCheckPermission("system:logOperation:query")
    @GetMapping("/page")
    public IPage<LogOperation> page(Page<LogOperation> page, LogOperation logOperation) {
        return logOperationService.page(page, logOperation);
    }

    /**
     * <p>
     * Get LogOperation list by LogOperation
     * </p>
     * @param logOperation {@link LogOperation}
     * @return List<LogOperation>
     */
    @SaCheckPermission("system:logOperation:query")
    @GetMapping("/list")
    public List<LogOperation> list(LogOperation logOperation) {
        return logOperationService.list(new LambdaQueryWrapper<>(logOperation));
    }

    /**
     * <p>
     * Get LogOperation by id
     * </p>
     * @param id id
     * @return LogOperation
     */
    @SaCheckPermission("system:logOperation:query")
    @GetMapping("/{id}")
    public LogOperation getById(@PathVariable("id") Long id) {
        return logOperationService.getById(id);
    }

    /**
     * <p>
     * Add LogOperation
     * </p>
     * @param logOperation {@link LogOperation}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:logOperation:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody LogOperation logOperation) {
        boolean bool = logOperationService.save(logOperation);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update LogOperation
     * </p>
     * @param logOperation {@link LogOperation}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:logOperation:update")
    @PostMapping("update")
    public R<Void> update(@Valid @RequestBody LogOperation logOperation) {
        boolean bool = logOperationService.updateById(logOperation);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete LogOperation by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:logOperation:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logOperationService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

