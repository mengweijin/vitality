package com.github.mengweijin.vitality.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.system.domain.entity.DictType;
import com.github.mengweijin.vitality.system.service.DictTypeService;
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
 *  DictType Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/system/dict-type")
public class DictTypeController {

    private DictTypeService dictTypeService;

    /**
     * <p>
     * Get DictType page by DictType
     * </p>
     * @param page page
     * @param dictType {@link DictType}
     * @return Page<DictType>
     */
    @SaCheckPermission("system:dictType:query")
    @GetMapping("/page")
    public IPage<DictType> page(Page<DictType> page, DictType dictType) {
        return dictTypeService.page(page, dictType);
    }

    /**
     * <p>
     * Get DictType list by DictType
     * </p>
     * @param dictType {@link DictType}
     * @return List<DictType>
     */
    @SaCheckPermission("system:dictType:query")
    @GetMapping("/list")
    public List<DictType> list(DictType dictType) {
        return dictTypeService.list(new QueryWrapper<>(dictType));
    }

    /**
     * <p>
     * Get DictType by id
     * </p>
     * @param id id
     * @return DictType
     */
    @SaCheckPermission("system:dictType:query")
    @GetMapping("/{id}")
    public DictType getById(@PathVariable("id") Long id) {
        return dictTypeService.getById(id);
    }

    /**
     * <p>
     * Add DictType
     * </p>
     * @param dictType {@link DictType}
     */
    @SaCheckPermission("system:dictType:create")
    @PostMapping
    public R<Void> add(@Valid @RequestBody DictType dictType) {
        boolean bool = dictTypeService.save(dictType);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Update DictType
     * </p>
     * @param dictType {@link DictType}
     */
    @SaCheckPermission("system:dictType:update")
    @PutMapping
    public R<Void> update(@Valid @RequestBody DictType dictType) {
        boolean bool = dictTypeService.updateById(dictType);
        return R.ajax(bool);
    }

    /**
     * <p>
     * Delete DictType by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @SaCheckPermission("system:dictType:delete")
    @DeleteMapping("/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        int i = dictTypeService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.ajax(i);
    }

}

