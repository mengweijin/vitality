package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.File;
import com.github.mengweijin.system.service.FileService;
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
 *  File Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/file")
public class FileController {

    private FileService fileService;

    /**
     * <p>
     * Get File page by File
     * </p>
     * @param page page
     * @param file {@link File}
     * @return Page<File>
     */
    @SaCheckPermission("system:file:query")
    @GetMapping("/page")
    public IPage<File> page(Page<File> page, File file) {
        return fileService.page(page, file);
    }

    /**
     * <p>
     * Get File list by File
     * </p>
     * @param file {@link File}
     * @return List<File>
     */
    @SaCheckPermission("system:file:query")
    @GetMapping("/list")
    public List<File> list(File file) {
        return fileService.list(new QueryWrapper<>(file));
    }

    /**
     * <p>
     * Get File by id
     * </p>
     * @param id id
     * @return File
     */
    @SaCheckPermission("system:file:query")
    @GetMapping("/{id}")
    public File getById(@PathVariable("id") Long id) {
        return fileService.getById(id);
    }

    /**
     * <p>
     * Add File
     * </p>
     * @param file {@link File}
     */
    @SaCheckPermission("system:file:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody File file) {
        boolean bool = fileService.save(file);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update File
     * </p>
     * @param file {@link File}
     */
    @SaCheckPermission("system:file:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody File file) {
        boolean bool = fileService.updateById(file);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete File by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:file:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = fileService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

