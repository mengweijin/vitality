package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.LogError;
import com.github.mengweijin.vitality.system.service.LogErrorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/system/log-error")
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
        return logErrorService.list(new QueryWrapper<>(logError));
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
    @SaCheckPermission("system:logError:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody LogError logError) {
        boolean bool = logErrorService.save(logError);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update LogError
     * </p>
     * @param logError {@link LogError}
     */
    @SaCheckPermission("system:logError:update")
    @PutMapping
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
    @SaCheckPermission("system:logError:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = logErrorService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

