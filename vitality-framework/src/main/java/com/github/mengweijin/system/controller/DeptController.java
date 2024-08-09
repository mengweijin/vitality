package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.domain.entity.Dept;
import com.github.mengweijin.system.service.DeptService;
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
 *  Dept Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/dept")
public class DeptController {

    private DeptService deptService;

    /**
     * <p>
     * Get Dept page by Dept
     * </p>
     * @param page page
     * @param dept {@link Dept}
     * @return Page<Dept>
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping("/page")
    public IPage<Dept> page(Page<Dept> page, Dept dept) {
        return deptService.page(page, dept);
    }

    /**
     * <p>
     * Get Dept list by Dept
     * </p>
     * @param dept {@link Dept}
     * @return List<Dept>
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping("/list")
    public List<Dept> list(Dept dept) {
        return deptService.list(new QueryWrapper<>(dept));
    }

    /**
     * <p>
     * Get Dept by id
     * </p>
     * @param id id
     * @return Dept
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping("/{id}")
    public Dept getById(@PathVariable("id") Long id) {
        return deptService.getById(id);
    }

    /**
     * <p>
     * Add Dept
     * </p>
     * @param dept {@link Dept}
     */
    @SaCheckPermission("system:dept:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody Dept dept) {
        boolean bool = deptService.save(dept);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update Dept
     * </p>
     * @param dept {@link Dept}
     */
    @SaCheckPermission("system:dept:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody Dept dept) {
        boolean bool = deptService.updateById(dept);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete Dept by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:dept:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = deptService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

