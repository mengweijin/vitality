package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.framework.util.DownLoadUtils;
import com.github.mengweijin.system.domain.entity.Oss;
import com.github.mengweijin.system.service.OssService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.FileInputStream;
import java.io.IOException;
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
@RequestMapping("/system/oss")
public class OssController {

    private OssService ossService;

    @PostMapping("/upload")
    public List<Oss> upload(HttpServletRequest request) {
        return ossService.upload(request);
    }

    /**
     * @param id id in table VTL_FILE
     */
    @GetMapping("/download/{id}")
    public R<Void> download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Oss oss = ossService.getById(id);
        if(oss == null) {
            return R.failure("No file was found in database! id=" + id);
        }
        try(FileInputStream in = new FileInputStream(oss.getStoragePath())) {
            DownLoadUtils.download(in, oss.getName(), request, response);
            return R.success();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     * Get File page by File
     * </p>
     * @param page page
     * @param oss {@link Oss}
     * @return Page<File>
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/page")
    public IPage<Oss> page(Page<Oss> page, Oss oss) {
        return ossService.page(page, oss);
    }

    /**
     * <p>
     * Get File list by File
     * </p>
     * @param oss {@link Oss}
     * @return List<File>
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/list")
    public List<Oss> list(Oss oss) {
        return ossService.list(new QueryWrapper<>(oss));
    }

    /**
     * <p>
     * Get File by id
     * </p>
     * @param id id
     * @return File
     */
    @SaCheckPermission("system:oss:query")
    @GetMapping("/{id}")
    public Oss getById(@PathVariable("id") Long id) {
        return ossService.getById(id);
    }

    /**
     * <p>
     * Add File
     * </p>
     * @param oss {@link Oss}
     */
    @SaCheckPermission("system:oss:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody Oss oss) {
        boolean bool = ossService.save(oss);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update File
     * </p>
     * @param oss {@link Oss}
     */
    @SaCheckPermission("system:oss:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody Oss oss) {
        boolean bool = ossService.updateById(oss);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete File by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:oss:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = ossService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

